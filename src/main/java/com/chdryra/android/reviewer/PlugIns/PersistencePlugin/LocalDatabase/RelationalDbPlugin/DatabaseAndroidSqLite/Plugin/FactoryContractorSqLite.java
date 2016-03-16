/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDbPlugin.DatabaseAndroidSqLite.Plugin;



import android.content.Context;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDbPlugin.Api.ContractorDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDbPlugin.Api.FactoryContractor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDbPlugin.DatabaseAndroidSqLite.Implementation.ContractorSqLite;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDbPlugin.DatabaseAndroidSqLite.Implementation.EntryToStringConverter;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDbPlugin.DatabaseAndroidSqLite.Implementation.FactoryTransactorSqLite;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDbPlugin.DatabaseAndroidSqLite.Implementation.RowToValuesConverter;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDbPlugin.DatabaseAndroidSqLite.Implementation.SqLiteTypeDefinitions;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDbPlugin.DatabaseAndroidSqLite.Implementation.TablesSql;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb.Interfaces.DbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb.Interfaces.DbSpecification;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryContractorSqLite implements FactoryContractor{
    private FactoryTransactorSqLite mTransactorFactory;
    private TablesSql mTableMaker;

    public FactoryContractorSqLite() {
        mTransactorFactory = new FactoryTransactorSqLite(new RowToValuesConverter(), new EntryToStringConverter());
        mTableMaker = new TablesSql(new SqLiteTypeDefinitions());
    }

    @Override
    public <T extends DbContract> ContractorDb<T> newContractor(Context context, DbSpecification<T> spec) {
        return new ContractorSqLite<>(context, spec, mTableMaker, mTransactorFactory);
    }
}
