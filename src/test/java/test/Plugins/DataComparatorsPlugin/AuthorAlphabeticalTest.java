/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.DataComparatorsPlugin;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation.AuthorAlphabetical;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Factories.AuthorIdGenerator;
import com.chdryra.android.testutils.RandomString;

import org.junit.Test;

/**
 * Created by: Rizwan Choudrey
 * On: 11/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class AuthorAlphabeticalTest extends ComparatorTest<DataAuthor> {

    public AuthorAlphabeticalTest() {
        super(new AuthorAlphabetical());
    }

    @Test
    public void alphabeticalAscendingDifferentFirstLetter() {
        DataAuthor A = new DatumAuthor("A" + RandomString.nextWord(), AuthorIdGenerator.newId());
        DataAuthor B = new DatumAuthor("B" + RandomString.nextWord(), AuthorIdGenerator.newId());
        DataAuthor C = new DatumAuthor("C" + RandomString.nextWord(), AuthorIdGenerator.newId());

        ComparatorTester<DataAuthor> tester = newComparatorTester();
        tester.testFirstSecond(A, B);
        tester.testFirstSecond(B, C);
        tester.testFirstSecond(A, C);
    }

    @Test
    public void alphabeticalAscendingStartingStemSame() {
        String name1 = RandomString.nextWord();
        String name2 = name1 + RandomString.nextWord();
        DataAuthor author1 = new DatumAuthor(name1, AuthorIdGenerator.newId());
        DataAuthor author2 = new DatumAuthor(name2, AuthorIdGenerator.newId());

        ComparatorTester<DataAuthor> tester = newComparatorTester();
        if(name1.compareToIgnoreCase(name2) < 0) {
            tester.testFirstSecond(author1, author2);
        } else if(name1.compareToIgnoreCase(name2) > 0){
            tester.testFirstSecond(author2, author1);
        } else {
            tester.testEquals(author2, author1);
        }
    }

    @Test
    public void comparatorEquality() {
        String name = RandomString.nextWord();
        DataAuthor author = new DatumAuthor(name, AuthorIdGenerator.newId());
        DataAuthor author2 = new DatumAuthor(name, AuthorIdGenerator.newId());

        ComparatorTester<DataAuthor> tester = newComparatorTester();
        tester.testEquals(author, author);
        tester.testEquals(author, author2);
    }

    @Test
    public void comparatorEqualityIgnoresCase() {
        String name = RandomString.nextWord();
        DataAuthor author = new DatumAuthor(name, AuthorIdGenerator.newId());
        DataAuthor authorLower = new DatumAuthor(name.toLowerCase(), AuthorIdGenerator.newId());
        DataAuthor authorUpper = new DatumAuthor(name.toUpperCase(), AuthorIdGenerator.newId());

        ComparatorTester<DataAuthor> tester = newComparatorTester();
        tester.testEquals(author, authorLower);
        tester.testEquals(author, authorUpper);
    }
}
