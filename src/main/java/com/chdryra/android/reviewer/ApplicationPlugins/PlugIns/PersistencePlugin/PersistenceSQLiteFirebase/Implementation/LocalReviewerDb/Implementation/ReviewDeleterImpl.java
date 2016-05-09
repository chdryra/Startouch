/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.LocalReviewerDb.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTag;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTagCollection;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.ReviewDeleterDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowComment;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowFact;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowImage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowLocation;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.PersistenceSQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowTag;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.FactoryDbTableRow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Api.TableTransactor;

/**
 * Created by: Rizwan Choudrey
 * On: 24/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDeleterImpl implements ReviewDeleterDb {
    private FactoryDbTableRow mRowFactory;

    public ReviewDeleterImpl(FactoryDbTableRow rowFactory) {
        mRowFactory = rowFactory;
    }

    @Override
    public boolean deleteReviewFromDb(RowReview row, TagsManager tagsManager,
                                      ReviewerDb db, TableTransactor transactor) {
        String idString = row.getReviewId().toString();

        deleteFromTable(db.getImagesTable(),
                asClause(RowImage.class, RowImage.REVIEW_ID, idString), transactor);
        deleteFromTable(db.getLocationsTable(),
                asClause(RowLocation.class, RowLocation.REVIEW_ID, idString), transactor);
        deleteFromTable(db.getFactsTable(),
                asClause(RowFact.class, RowFact.REVIEW_ID, idString), transactor);
        deleteFromTable(db.getCommentsTable(),
                asClause(RowComment.class, RowComment.REVIEW_ID, idString), transactor);
        deleteFromTable(db.getReviewsTable(),
                asClause(RowReview.class, RowReview.REVIEW_ID, idString), transactor);
        deleteCriteria(db.getReviewsTable(), idString, transactor);
        deleteTagsIfNecessary(db.getTagsTable(), idString, tagsManager, transactor);
        deleteAuthorIfNecessary(db, row, transactor);

        return true;
    }

    private void deleteCriteria(DbTable<RowReview> reviewsTable,
                                String reviewId,
                                TableTransactor transactor) {
        RowEntry<RowReview, String> reviewIsParent
                = asClause(RowReview.class, RowReview.PARENT_ID, reviewId);
        TableRowList<RowReview> rows
                = transactor.getRowsWhere(reviewsTable, reviewIsParent, mRowFactory);
        for (RowReview row : rows) {
            RowEntry<RowReview, String> idClause = asClause(RowReview.class, RowReview.REVIEW_ID,
                    row.getReviewId().toString());
            deleteFromTable(reviewsTable, idClause, transactor);
        }
    }

    private void deleteTagsIfNecessary(DbTable<RowTag> tagsTable, String reviewId,
                                       TagsManager tagsManager, TableTransactor transactor) {
        ItemTagCollection tags = tagsManager.getTags(reviewId);
        for (ItemTag tag : tags) {
            if (tag.getNumberTagged() == 1) {
                RowEntry<RowTag, String> tagClause
                        = asClause(RowTag.class, RowTag.TAG, tag.getTag());
                deleteFromTable(tagsTable, tagClause, transactor);
            }
        }
    }

    private void deleteAuthorIfNecessary(ReviewerDb db, RowReview row,
                                         TableTransactor transactor) {
        String userId = row.getAuthorId();
        DbTable<RowReview> reviewsTable = db.getReviewsTable();
        RowEntry<RowReview, String> clause = asClause(RowReview.class, RowReview.USER_ID, userId);

        TableRowList<RowReview> authored = transactor.getRowsWhere(reviewsTable, clause, mRowFactory);
        if (authored.size() == 0) {
            RowEntry<RowAuthor, String> userIdClause
                    = asClause(RowAuthor.class, RowAuthor.USER_ID, userId);
            deleteFromTable(db.getAuthorsTable(), userIdClause, transactor);
        }
    }

    private <DbRow extends DbTableRow> void deleteFromTable(DbTable<DbRow> table,
                                                            RowEntry<DbRow, ?> clause,
                                                            TableTransactor transactor) {
        transactor.deleteRowsWhere(table, clause);
    }

    @NonNull
    private <DbRow extends DbTableRow, T> RowEntry<DbRow, T> asClause(Class<DbRow> rowClass,
                                                               ColumnInfo<T> column, T value) {
        return new RowEntryImpl<>(rowClass, column, value);
    }
}