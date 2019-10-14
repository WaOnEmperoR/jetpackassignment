package id.govca.jetpackassignment.fragment;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import id.govca.jetpackassignment.EspressoIdlingResource;
import id.govca.jetpackassignment.R;
import id.govca.jetpackassignment.testing.SingleFragmentActivity;
import id.govca.jetpackassignment.utils.RecyclerViewItemCountAssertion;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class TVShowFragmentTest {
    @Rule
    public ActivityTestRule<SingleFragmentActivity> activityRule = new ActivityTestRule<>(SingleFragmentActivity.class);
    private TVShowFragment tvShowFragment = new TVShowFragment();

    @Before
    public void setUp() {
        activityRule.getActivity().setFragment(tvShowFragment);
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResourcey());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResourcey());
    }

    @Test
    public void loadMovies() {
        onView(withId(R.id.recyclerView_tv_show)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerView_tv_show)).check(new RecyclerViewItemCountAssertion(20));
    }

}