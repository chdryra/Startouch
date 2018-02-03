/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Plugin;



import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api.BackendPlugin;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Factories.BackendInfoConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Factories.BackendReviewConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.BackendValidator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation.UserProfileConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Factories.FactoryAuthorProfile;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Factories.FactoryFbReviewsRepo;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Factories.FactoryFbCollection;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Factories.FactoryUserAccount;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Factories.FbDataReferencer;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Factories.FbReviewReferencer;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.ConverterNamedAuthorId;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.FbAuthenticator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.FbAuthorsRepo;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.FbReviewsRepo;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.FbStructUsersLed;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.FbUserAccounts;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Implementation.FirebaseBackend;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.BackendFirebase.Interfaces.FirebaseStructure;
import com.chdryra.android.reviewer.Authentication.Factories.FactoryAuthorProfileSnapshot;
import com.chdryra.android.reviewer.Authentication.Implementation.AccountsManagerImpl;
import com.chdryra.android.reviewer.Authentication.Interfaces.AccountsManager;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAccounts;
import com.chdryra.android.reviewer.Authentication.Interfaces.UserAuthenticator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepo;
import com.firebase.client.Firebase;

/**
 * Created by: Rizwan Choudrey
 * On: 21/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendFirebase implements BackendPlugin {
    private final Firebase mDatabase;
    private final FirebaseStructure mStructure;
    private final UserProfileConverter mConverter;
    private final FbDataReferencer mDataReferencer;
    private final AuthorsRepo mAuthorsRepo;
    private final FactoryAuthorProfileSnapshot mFactoryProfileSnapshot;
    private final FactoryAuthorProfile mFactoryAuthorProfile;

    public BackendFirebase(Context context) {
        Firebase.setAndroidContext(context);
        mDatabase = new Firebase(FirebaseBackend.ROOT);
        mStructure = new FbStructUsersLed();
        mFactoryProfileSnapshot = new FactoryAuthorProfileSnapshot();
        mConverter = new UserProfileConverter(mFactoryProfileSnapshot);
        mDataReferencer = new FbDataReferencer();
        mFactoryAuthorProfile = new FactoryAuthorProfile(mDatabase, mStructure, mDataReferencer, mConverter);
        mAuthorsRepo = new FbAuthorsRepo(mDatabase, mStructure, new ConverterNamedAuthorId(), mDataReferencer, mFactoryAuthorProfile);
    }

    @Override
    public ReviewsRepo getReviews(ModelContext model,
                                  DataValidator validator,
                                  com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsRepo repoFactory,
                                  ReviewsCache cache) {
        BackendInfoConverter infoConverter = new BackendInfoConverter();
        BackendValidator beValidator = new BackendValidator(validator);
        BackendReviewConverter reviewConverter
                = new BackendReviewConverter(beValidator, model.getReviewsFactory());
        FbReviewReferencer referencer
                = new FbReviewReferencer(mDataReferencer, infoConverter, reviewConverter, cache);

        ConverterEntry entryConverter = new ConverterEntry();
        FactoryFbCollection collectionFactory = new FactoryFbCollection(mStructure, entryConverter, infoConverter, referencer);

        FactoryFbReviewsRepo authorsDbFactory = new FactoryFbReviewsRepo(reviewConverter, beValidator,
                entryConverter, referencer, repoFactory.newDereferencer(), cache, collectionFactory);

        ReviewsRepo masterRepo = new FbReviewsRepo(mDatabase, mStructure,
                entryConverter, referencer, repoFactory.newDereferencer(), authorsDbFactory);

        collectionFactory.setMasterRepo(masterRepo);

        return masterRepo;
    }

    @Override
    public AccountsManager getAccounts() {
        UserAccounts accounts = new FbUserAccounts(mDatabase, mStructure, mDataReferencer, mConverter,
                mFactoryAuthorProfile, mFactoryProfileSnapshot,
                mAuthorsRepo, new FactoryUserAccount());
        UserAuthenticator authenticator = new FbAuthenticator(mDatabase, accounts, mConverter);

        return new AccountsManagerImpl(authenticator, accounts);
    }

    @Override
    public AuthorsRepo getAuthors() {
        return mAuthorsRepo;
    }
}