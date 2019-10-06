package id.govca.jetpackassignment.fragment;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import id.govca.jetpackassignment.R;
import id.govca.jetpackassignment.testing.SingleFragmentActivity;
import id.govca.jetpackassignment.utils.RecyclerViewItemCountAssertion;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MovieFragmentTest {
    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityRule = new ActivityTestRule<>(SingleFragmentActivity.class);
    private MovieFragment movieFragment = new MovieFragment();

    @Before
    public void setUp() {
        activityRule.getActivity().setFragment(movieFragment);
    }

    @Test
    public void loadMovies() throws InterruptedException {
        onView(withId(R.id.recyclerView_movie)).check(matches(isDisplayed()));
        // wait for movie data to be loaded
        Thread.sleep(5000);
        onView(withId(R.id.recyclerView_movie)).check(new RecyclerViewItemCountAssertion(20));
    }

}