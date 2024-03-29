package id.govca.jetpackassignment.rest;

import id.govca.jetpackassignment.pojo.MovieDetail;
import id.govca.jetpackassignment.pojo.MovieList;
import id.govca.jetpackassignment.pojo.TVShowDetail;
import id.govca.jetpackassignment.pojo.TVShowList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("discover/movie")
    Observable<MovieList> RxGetMovieList(@Query("api_key") String apiKey,
                                         @Query("language") String language,
                                         @Query("page") int page );

    @GET("discover/movie")
    Observable<MovieList> RxGetMoviesToday(@Query("api_key") String apiKey,
                                           @Query("primary_release_date.gte") String gte,
                                           @Query("primary_release_date.lte") String lte);

    @GET("search/movie")
    Observable<MovieList> RxSearchMovies(@Query("query") String query,
                                         @Query("api_key") String apiKey,
                                         @Query("language") String language);

    @GET("discover/tv")
    Observable<TVShowList> RxGetTVShowList(@Query("api_key") String apiKey,
                                           @Query("language") String language,
                                           @Query("page") int page);

    @GET("search/tv")
    Observable<TVShowList> RxSearchTVShows(@Query("query") String query,
                                           @Query("api_key") String apiKey,
                                           @Query("language") String language);

    @GET("movie/{Id}")
    Observable<MovieDetail> RxMovieDetails(@Path("Id") int id,
                                           @Query("api_key") String apiKey,
                                           @Query("language") String language);

    @GET("tv/{Id}")
    Observable<TVShowDetail> RxTVShowDetails(@Path("Id") int id,
                                             @Query("api_key") String apiKey,
                                             @Query("language") String language);


}
