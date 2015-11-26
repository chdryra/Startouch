package com.chdryra.android.reviewer.Database.Factories;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbHelper;
import com.chdryra.android.reviewer.Database.Implementation.ReviewerDbImpl;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewLoader;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.Database.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.Model.Interfaces.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewerDb {
    private FactoryDbTableRow mRowfactory;

    public FactoryReviewerDb(FactoryDbTableRow rowfactory) {
        mRowfactory = rowfactory;
    }

    public ReviewerDb newDatabase(DbHelper<ReviewerDbContract> dbHelper,
                                  ReviewLoader reviewLoader,
                                  TagsManager tagsManager,
                                  DataValidator dataValidator) {
        return new ReviewerDbImpl(dbHelper, reviewLoader, mRowfactory, tagsManager, dataValidator);
    }
}