package id.govca.jetpackassignment;

import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class DetailTVShowActivityTest {
    @Rule
    public ActivityTestRule<DetailActivity> activityTestRule = new ActivityTestRule<DetailActivity>(DetailActivity.class){
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent result = new Intent(targetContext, DetailActivity.class);
            result.putExtra("Movie_ID", 456);
            result.putExtra("Category", 1);
            return result;
        }
    };

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResourcey());
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResourcey());
    }

    @Test
    public void loadMovie() {
        String homepage = "http://www.thesimpsons.com/";
        String overview = "Set in Springfield, the average American town, the show focuses on the antics and everyday adventures of the Simpson family; Homer, Marge, Bart, Lisa and Maggie, as well as a virtual cast of thousands. Since the beginning, the series has been a pop culture icon, attracting hundreds of celebrities to guest star. The show has also made name for itself in its fearless satirical take on politics, media and American life in general.";
        String first_air_date = "1989-12-17";

        onView(withId(R.id.tv_homepage_content)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_homepage_content)).check(matches(withText(homepage)));
        onView(withId(R.id.tv_movie_year_content)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_movie_year_content)).check(matches(withText(first_air_date)));
        onView(withId(R.id.tv_movie_synopsis_content)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_movie_synopsis_content)).check(matches(withText(overview)));
    }
}
