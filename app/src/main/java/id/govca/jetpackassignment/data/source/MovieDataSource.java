package id.govca.jetpackassignment.data.source;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.govca.jetpackassignment.pojo.Movie;
import id.govca.jetpackassignment.pojo.MovieDetail;
import id.govca.jetpackassignment.pojo.TVShow;
import id.govca.jetpackassignment.pojo.TVShowDetail;

public interface MovieDataSource {
    LiveData<List<Movie>> getAllMovies(String param_lang);

    LiveData<MovieDetail> getMovieDetail(String param_lang, int movieId);

    LiveData<List<TVShow>> getAllTVShows(String param_lang);

    LiveData<TVShowDetail> getTVShowDetail(String param_lang, int tvShowId);
}
