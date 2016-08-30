/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation;


import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Factories.FactoryReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.SimpleRefComment;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefComment;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.Utils.CommentFormatter;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/08/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class SimpleRefFirstSentence extends SimpleRefComment {
    public SimpleRefFirstSentence(RefComment parent, FactoryReference referenceFactory) {
        super(parent, referenceFactory, new FirstSentenceDereferencer(parent));
    }

    @Override
    public RefComment getFirstSentence() {
        return this;
    }

    @Override
    public void toSentences(SentencesCallback callback) {
        IdableList<RefComment> sentences = new IdableDataList<>(getReviewId());
        sentences.add(this);
        callback.onSentenceReferences(sentences);
    }

    private static class FirstSentenceDereferencer implements Dereferencer<DataComment> {
        private RefComment mParent;

        public FirstSentenceDereferencer(RefComment parent) {
            mParent = parent;
        }

        @Override
        public ReviewId getReviewId() {
            return mParent.getReviewId();
        }

        @Override
        public void dereference(final DereferenceCallback<DataComment> callback) {
            mParent.dereference(new DereferenceCallback<DataComment>() {
                @Override
                public void onDereferenced(@Nullable DataComment data, CallbackMessage message) {
                    DataComment sentence = null;
                    if (data != null && !message.isError()) {
                        String comment = data.getComment();
                        ArrayList<String> split = CommentFormatter.split(comment, true);
                        sentence = new DatumComment(getReviewId(), split.get(0), mParent.isHeadline());
                    }
                    callback.onDereferenced(sentence, message);
                }
            });
        }
    }
}
