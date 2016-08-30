/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.LocalReviewerDb;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Implementation.RowAuthorImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Interfaces.RowAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Factories.AuthorIdGenerator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DefaultNamedAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.AuthorIdParcelable;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.NamedAuthor;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 21/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowAuthorImplTest extends RowTableBasicTest<RowAuthor, RowAuthorImpl>{

    public RowAuthorImplTest() {
        super(RowAuthor.AUTHOR_ID.getName(), 2);
    }

    @Test
    public void constructionWithDataAuthorAndGetters() {
        AuthorId id = AuthorIdGenerator.newId();
        String name = RandomString.nextWord();
        NamedAuthor author = new DefaultNamedAuthor(name, id);

        RowAuthorImpl row = new RowAuthorImpl(author);
        assertThat(row.getAuthorId().toString(), is(id.toString()));
        assertThat(row.getName(), is(name));
    }

    @Test
    public void constructionWithRowValuesAndGetters() {
        RowAuthor reference = newRow();

        RowValuesForTest values = new RowValuesForTest();
        values.put(RowAuthor.AUTHOR_ID, reference.getAuthorId().toString());
        values.put(RowAuthor.AUTHOR_NAME, reference.getName());

        RowAuthorImpl row = new RowAuthorImpl(values);
        assertThat(row.hasData(new DataValidator()), is(true));

        assertThat(row.getAuthorId().toString(), is(reference.getAuthorId().toString()));
        assertThat(row.getName(), is(reference.getName()));
    }

    @Test
    public void constructionWithDataAuthorWithInvalidUserIdMakesRowAuthorInvalid() {
        NamedAuthor author = new DefaultNamedAuthor(RandomString.nextWord(), new AuthorIdParcelable(""));
        RowAuthorImpl row = new RowAuthorImpl(author);
        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithDataAuthorWithInvalidNameMakesRowAuthorInvalid() {
        NamedAuthor author = new DefaultNamedAuthor("", AuthorIdGenerator.newId());
        RowAuthorImpl row = new RowAuthorImpl(author);
        assertThat(row.hasData(new DataValidator()), is(false));
    }

    @Test
    public void constructionWithValidDataAuthorMakesRowAuthorValid() {
        NamedAuthor author = new DefaultNamedAuthor(RandomString.nextWord(), AuthorIdGenerator.newId());
        RowAuthorImpl row = new RowAuthorImpl(author);
        assertThat(row.hasData(new DataValidator()), is(true));
    }

    @Test
    public void iteratorReturnsDataInOrder() {
        RowAuthorImpl row = newRow();

        ArrayList<RowEntry<RowAuthor, ?>> entries = getRowEntries(row);

        assertThat(entries.size(), is(2));

        checkEntry(entries.get(0), RowAuthor.AUTHOR_ID, row.getAuthorId().toString());
        checkEntry(entries.get(1), RowAuthor.AUTHOR_NAME, row.getName());
    }

    @NonNull
    @Override
    protected RowAuthorImpl newRow() {
        AuthorId id = AuthorIdGenerator.newId();
        String name = RandomString.nextWord();
        NamedAuthor author = new DefaultNamedAuthor(name, id);

        return new RowAuthorImpl(author);
    }

    @Override
    protected String getRowId(RowAuthorImpl row) {
        return row.getAuthorId().toString();
    }
}
