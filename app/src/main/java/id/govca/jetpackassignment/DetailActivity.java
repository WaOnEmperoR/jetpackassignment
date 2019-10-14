package id.govca.jetpackassignment;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Locale;
import java.util.StringJoiner;

import id.govca.jetpackassignment.pojo.Genre;
import id.govca.jetpackassignment.pojo.MovieDetail;
import id.govca.jetpackassignment.pojo.TVShowDetail;
import id.govca.jetpackassignment.rest.Constants;
import id.govca.jetpackassignment.viewmodel.MovieViewModel;
import id.govca.jetpackassignment.viewmodel.TvShowViewModel;
import id.govca.jetpackassignment.viewmodel.ViewModelFactory;

public class DetailActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private View mProgressView;
    private View mScrollView;

    private int idThings, category;
    private TextView tv_name, tv_genres, tv_homepage, tv_year, tv_synopsis;
    private ImageView imgView_poster;

    private RatingBar ratingBar;

    Context context = GlobalApplication.getAppContext();

    private MovieViewModel movieViewModel;
    private TvShowViewModel tvShowViewModel;

    @NonNull
    private static MovieViewModel obtainMovieViewModel(AppCompatActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance();

        return ViewModelProviders.of(activity, factory).get(MovieViewModel.class);
    }

    @NonNull
    private static TvShowViewModel obtainTvShowViewModel(AppCompatActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance();

        return ViewModelProviders.of(activity, factory).get(TvShowViewModel.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle b = getIntent().getExtras();
        idThings = b.getInt("Movie_ID");
        category = b.getInt("Category");

        mProgressView = findViewById(R.id.progressBarDetail);
        mScrollView = findViewById(R.id.scrollViewDetail);

        tv_name = findViewById(R.id.tv_movie_name);
        ratingBar = findViewById(R.id.ratingBar);
        tv_genres = findViewById(R.id.tv_genres_content);
        tv_homepage = findViewById(R.id.tv_homepage_content);
        tv_year = findViewById(R.id.tv_movie_year_content);
        tv_synopsis = findViewById(R.id.tv_movie_synopsis_content);
        imgView_poster = findViewById(R.id.imgView_poster);

        if (category == 0)
        {
            EspressoIdlingResource.increment();

            Locale current = getResources().getConfiguration().locale;

            String param_lang = current.getLanguage() + "-" + current.getCountry();
            if (param_lang.equals("in-ID"))
            {
                param_lang = "id-ID";
            }

            movieViewModel = obtainMovieViewModel(this);

            movieViewModel.getListMoviesLiveData(param_lang, idThings).observe(this, movieDetail -> {
                showLoading(false);
                pseudoAdapterMovie(movieDetail);

                if (!EspressoIdlingResource.getEspressoIdlingResourcey().isIdleNow()) {
                    EspressoIdlingResource.decrement();
                }
            });
        }
        else if (category == 1)
        {
            EspressoIdlingResource.increment();

            Locale current = getResources().getConfiguration().locale;

            String param_lang = current.getLanguage() + "-" + current.getCountry();
            if (param_lang.equals("in-ID"))
            {
                param_lang = "id-ID";
            }

            tvShowViewModel = obtainTvShowViewModel(this);

            tvShowViewModel.getTvShowLiveData(param_lang, idThings).observe(this, tvShowDetail -> {
                showLoading(false);
                pseudoAdapterTVShow(tvShowDetail);

                if (!EspressoIdlingResource.getEspressoIdlingResourcey().isIdleNow()) {
                    EspressoIdlingResource.decrement();
                }
            });

        }
    }

    private Observer<MovieDetail> getMovieDetail = new Observer<MovieDetail>() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onChanged(MovieDetail movieDetail) {
            if (movieDetail!=null){
                pseudoAdapterMovie(movieDetail);
                showLoading(false);
            }
        }
    };

    private Observer<TVShowDetail> getTVShowDetail = new Observer<TVShowDetail>() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onChanged(TVShowDetail tvShowDetail) {
            if (tvShowDetail != null)
            {
                pseudoAdapterTVShow(tvShowDetail);
                showLoading(false);
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void pseudoAdapterMovie(final MovieDetail movieDetail){
        StringJoiner joiner = new StringJoiner(", ");
        Genre[] genres = movieDetail.getGenres();
        for (Genre genre : genres) {
            joiner.add(genre.getName());
        }

        tv_name.setText(movieDetail.getOriginal_title());
        tv_genres.setText(joiner.toString());
        tv_homepage.setText(movieDetail.getHomepage());
        tv_synopsis.setText(movieDetail.getOverview());
        tv_year.setText(movieDetail.getRelease_date());
        ratingBar.setRating((float) movieDetail.getVote_average()/2.0f);

        Glide
                .with(getBaseContext())
                .load(Constants.IMAGE_ROOT_LARGE + movieDetail.getPoster_path())
                .into(imgView_poster);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void pseudoAdapterTVShow(final TVShowDetail tvShowDetail)
    {
        StringJoiner joiner = new StringJoiner(", ");
        Genre[] genres = tvShowDetail.getGenres();
        for (Genre genre : genres) {
            joiner.add(genre.getName());
        }

        tv_name.setText(tvShowDetail.getName());
        tv_genres.setText(joiner.toString());
        tv_homepage.setText(tvShowDetail.getHomepage());
        tv_synopsis.setText(tvShowDetail.getOverview());
        tv_year.setText(tvShowDetail.getFirst_air_date());
        ratingBar.setRating((float) tvShowDetail.getVote_average()/2.0f);

        Glide
                .with(getBaseContext())
                .load(Constants.IMAGE_ROOT_LARGE + tvShowDetail.getPoster_path())
                .into(imgView_poster);
    }

    private void showLoading(Boolean state) {
        if (state) {
            mProgressView.setVisibility(View.VISIBLE);
            mScrollView.setVisibility(View.GONE);
        } else {
            mProgressView.setVisibility(View.GONE);
            mScrollView.setVisibility(View.VISIBLE);
        }
    }
}
