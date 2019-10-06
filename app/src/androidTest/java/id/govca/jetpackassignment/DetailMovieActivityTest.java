package id.govca.jetpackassignment;

import android.content.Context;
import android.content.Intent;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class DetailMovieActivityTest {

    @Rule
    public ActivityTestRule<DetailActivity> activityTestRule = new ActivityTestRule<DetailActivity>(DetailActivity.class){
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent result = new Intent(targetContext, DetailActivity.class);
            result.putExtra("Movie_ID", 475557);
            result.putExtra("Category", 0);
            return result;
        }
    };

    @Test
    public void loadMovie() throws InterruptedException {
        // waiting for movie data to be loaded
        Thread.sleep(3000);
        String release_date = "2019-10-02";
        String overview = "During the 1980s, a failed stand-up comedian is driven insane and turns to a life of crime and chaos in Gotham City while becoming an infamous psychopathic crime figure.";
        String homepage = "http://www.jokermovie.net/";

        onView(withId(R.id.tv_homepage_content)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_homepage_content)).check(matches(withText(homepage)));
        onView(withId(R.id.tv_movie_year_content)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_movie_year_content)).check(matches(withText(release_date)));
        onView(withId(R.id.tv_movie_synopsis_content)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_movie_synopsis_content)).check(matches(withText(overview)));
    }
}