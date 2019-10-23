package id.govca.jetpackassignment.data.source;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.govca.jetpackassignment.data.source.local.entity.Favorite;
import id.govca.jetpackassignment.pojo.Movie;
import id.govca.jetpackassignment.pojo.MovieDetail;
import id.govca.jetpackassignment.pojo.TVShow;
import id.govca.jetpackassignment.pojo.TVShowDetail;
import id.govca.jetpackassignment.vo.Resource;

public interface MovieDataSource {
    LiveData<List<Movie>> getAllMovies(String param_lang);

    LiveData<MovieDetail> getMovieDetail(String param_lang, int movieId);

    LiveData<List<TVShow>> getAllTVShows(String param_lang);

    LiveData<TVShowDetail> getTVShowDetail(String param_lang, int tvShowId);

    LiveData<List<Favorite>> getFavorites(int type);

    LiveData<Favorite> getFavoriteDetail(int type, int id);

    LiveData<Integer> checkFavorite(int type, int thingsId);

    LiveData<Integer> deleteFavorite(int type, int thingsId);

    LiveData<Long> insertFavorite(Favorite favorite);
}
