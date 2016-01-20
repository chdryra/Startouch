package test.Plugins.GenericDb;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Implementation.DbTableImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Interfaces.ForeignKeyConstraint;

/**
 * Created by: Rizwan Choudrey
 * On: 20/01/2016
 * Email: rizwan.choudrey@gmail.com
 */ //to test protected methods
class DbTableForTesting<T extends DbTableRow> extends DbTableImpl<T> {
    public DbTableForTesting(String tableName, Class<T> rowClass) {
        super(tableName, rowClass);
    }

    @Override
    public void addColumn(DbColumnDefinition column) {
        super.addColumn(column);
    }

    @Override
    public void addPrimaryKeyColumn(DbColumnDefinition column) {
        super.addPrimaryKeyColumn(column);
    }

    @Override
    public void addForeignKeyConstraint(ForeignKeyConstraint<? extends DbTableRow>
                                                    constraint) {
        super.addForeignKeyConstraint(constraint);
    }
}
