package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.DatabasePlugin;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.FactoryContractor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.TransactorTypeDefinitions;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Factories.FactoryContractorSqLite;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Implementation.TransactorTypeDefinitionsSqlLite;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DatabaseAndroidSqlLite implements DatabasePlugin {
    private static final TransactorTypeDefinitions TYPES = new TransactorTypeDefinitionsSqlLite();
    private static final String EXT = ".db";
    public static final FactoryContractorSqLite FACTORY = new FactoryContractorSqLite();

    @Override
    public TransactorTypeDefinitions getTypeDefinitions() {
        return TYPES;
    }

    @Override
    public FactoryContractor getContractorFactory() {
        return FACTORY;
    }

    @Override
    public String getDbNameExtension() {
        return EXT;
    }
}
