package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin
        .DatabaseAndroidSqLite.Implementation;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.TableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.FactoryDbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation.TableRowList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TableTransactorSqlLite implements TableTransactor {
    private final SQLiteDatabase mDb;
    private final FactoryDbTableRow mRowFactory;
    private final RowToValuesConverter mConverter;

    public TableTransactorSqlLite(SQLiteDatabase db,
                                  FactoryDbTableRow rowFactory,
                                  RowToValuesConverter converter) {
        mDb = db;
        mRowFactory = rowFactory;
        mConverter = converter;
    }

    @Override
    public void beginTransaction() {
        mDb.beginTransaction();
    }

    @Override
    public void endTransaction() {
        mDb.setTransactionSuccessful();
        mDb.endTransaction();
        mDb.close();
    }

    @Override
    public <DbRow extends DbTableRow> TableRowList<DbRow> getRowsWhere(DbTable<DbRow> table,
                                                               String col,
                                                               String val) {
        return getAllRowsWhere(table, col, val);
    }

    @Override
    public <DbRow extends DbTableRow> TableRowList<DbRow> loadTable(DbTable<DbRow> table) {
        return getAllRowsWhere(table, null, null);
    }

    @Override
    public <DbRow extends DbTableRow> boolean insertRow(DbRow row, DbTable<DbRow> table) {
        DbColumnDefinition idCol = table.getColumn(row.getRowIdColumnName());
        String id = row.getRowId();
        if (isIdInTable(id, idCol, table)) return false;

        String tableName = table.getName();
        try {
            mDb.insertOrThrow(tableName, null, convertRow(row));
            return true;
        } catch (SQLException e) {
            String message = id + " into " + tableName + " table ";
            throw new RuntimeException("Couldn't insert " + message, e);
        }
    }

    @Override
    public <DbRow extends DbTableRow> void insertOrReplaceRow(DbRow row, DbTable<DbRow> table) {
        DbColumnDefinition idCol = table.getColumn(row.getRowIdColumnName());
        String id = row.getRowId();
        String tableName = table.getName();
        if (isIdInTable(id, idCol, table)) {
            try {
                mDb.replaceOrThrow(tableName, null, convertRow(row));
            } catch (SQLException e) {
                String message = id + " in " + tableName + " table ";
                throw new RuntimeException("Couldn't replace " + message, e);
            }
        } else {
            insertRow(row, table);
        }
    }

    @Override
    public void deleteRows(DbTable<?> table, String col, String val) {
        String tableName = table.getName();
        try {
            mDb.delete(tableName, col + SQL.BIND_STRING, new String[]{val});
        } catch (SQLException e) {
            String message = val + " from " + tableName + " table ";
            throw new RuntimeException("Couldn't delete " + message, e);
        }
    }

    @Override
    public boolean isIdInTable(String id, DbColumnDefinition idCol, DbTable<?> table) {
        String pkCol = idCol.getName();
        Cursor cursor = getCursorWhere(table.getName(), pkCol, id);

        boolean hasRow = false;
        if (cursor != null) {
            if (cursor.moveToFirst()) hasRow = true;
            cursor.close();
        }

        return hasRow;
    }

    private <T extends DbTableRow> T toDbTableRow(DbTable<T> table) {
        return mRowFactory.emptyRow(table.getRowClass());
    }

    @NonNull
    private <T extends DbTableRow> TableRowList<T> getAllRowsWhere(DbTable<T> table,
                                                                   @Nullable String col,
                                                                   @Nullable String val) {
        Cursor cursor = getFromTableWhere(table.getName(), col, val);
        TableRowList<T> list = new TableRowList<>();
        if (cursor == null || cursor.getCount() == 0) {
            list.add(toDbTableRow(table));
            return list;
        }

        while (cursor.moveToNext()) {
            list.add(toDbTableRow(table, cursor));
        }
        cursor.close();

        return list;
    }

    private <T extends DbTableRow> T toDbTableRow(DbTable<T> table, Cursor cursor) {
        return mRowFactory.newRow(new CursorRowValues(cursor), table.getRowClass());
    }

    private <T extends DbTableRow> ContentValues convertRow(T row) {
        return mConverter.convert(row);
    }

    private Cursor getFromTableWhere(String table, @Nullable String column, @Nullable String
            value) {
        boolean isNull = value == null;
        String val = isNull ? SQL.SPACE + SQL.IS_NULL : SQL.SPACE + SQL.BIND_STRING;
        String whereClause = column != null ? " " + SQL.WHERE + column + val : "";
        String query = SQL.SELECT + SQL.ALL + SQL.FROM + table + whereClause;
        String[] args = isNull ? null : new String[]{value};
        return mDb.rawQuery(query, args);
    }

    @Nullable
    private Cursor getCursorWhere(String table, String pkColumn, String pkValue) {
        Cursor cursor = getFromTableWhere(table, pkColumn, pkValue);
        if (cursor != null && cursor.getCount() > 1) {
            cursor.close();
            throw new IllegalStateException("Cannot have more than 1 row with same primary key!");
        }

        return cursor;
    }
}
