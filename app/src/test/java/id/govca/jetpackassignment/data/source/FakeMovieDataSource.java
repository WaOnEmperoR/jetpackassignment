package id.govca.jetpackassignment.data.source;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.govca.jetpackassignment.pojo.Movie;
import id.govca.jetpackassignment.pojo.MovieDetail;
import id.govca.jetpackassignment.pojo.TVShow;
import id.govca.jetpackassignment.pojo.TVShowDetail;

public interface FakeMovieDataSource {
    List<Movie> getAllMovies();

    MovieDetail getMovieDetail(int movieId);

    List<TVShow> getAllTVShows();

    TVShowDetail getTVShowDetail(int tvShowId);
}
