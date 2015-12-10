package test.Model.ReviewsModel.Factories;

import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNodeComponent;

import org.junit.Test;

import test.TestUtils.RandomReview;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.*;

/**
 * Created by: Rizwan Choudrey
 * On: 09/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewNodeTest {
    @Test
    public void createReviewNodeComponentNotAverageReturnsObjectWithExpectedAttributes() {
        FactoryReviewNode factory= new FactoryReviewNode();
        Review review = RandomReview.nextReview();
        ReviewNodeComponent node = factory.createReviewNodeComponent(review, false);
        assertThat(node, notNullValue());
        assertThat(node.getReview(), is(review));
        assertThat(node.isRatingAverageOfChildren(), is(false));
    }

    @Test
    public void createReviewNodeComponentAverageReturnsObjectWithExpectedAttributes() {
        FactoryReviewNode factory= new FactoryReviewNode();
        Review review = RandomReview.nextReview();
        ReviewNodeComponent node = factory.createReviewNodeComponent(review, true);
        assertThat(node, notNullValue());
        assertThat(node.getReview(), is(review));
        assertThat(node.isRatingAverageOfChildren(), is(true));
    }

    @Test
    public void createReviewNodeNotAverageReturnsObjectWithExpectedAttributes() {
        FactoryReviewNode factory= new FactoryReviewNode();
        Review review = RandomReview.nextReview();
        ReviewNode node = factory.createReviewNode(review, false);
        assertThat(node, notNullValue());
        assertThat(node.getReview(), is(review));
        assertThat(node.isRatingAverageOfChildren(), is(false));
    }

    @Test
    public void createReviewNodeAverageReturnsObjectWithExpectedAttributes() {
        FactoryReviewNode factory= new FactoryReviewNode();
        Review review = RandomReview.nextReview();
        ReviewNode node = factory.createReviewNode(review, true);
        assertThat(node, notNullValue());
        assertThat(node.getReview(), is(review));
        assertThat(node.isRatingAverageOfChildren(), is(true));
    }

    @Test
    public void createReviewNodeReturnsObjectWithExpectedAttributes() {
        FactoryReviewNode factory= new FactoryReviewNode();
        Review review = RandomReview.nextReview();
        ReviewNodeComponent component = factory.createReviewNodeComponent(review, false);
        ReviewNode node = factory.createReviewNode(component);
        assertThat(node, notNullValue());
        assertThat(node.getReview(), is(review));
        assertThat(node.isRatingAverageOfChildren(), is(false));
    }
}
