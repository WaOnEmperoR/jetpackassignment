package id.govca.jetpackassignment.data.source.remote;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import java.util.List;

import id.govca.jetpackassignment.pojo.Movie;
import id.govca.jetpackassignment.pojo.MovieDetail;
import id.govca.jetpackassignment.pojo.TVShowDetail;
import id.govca.jetpackassignment.pojo.TVShowList;
import io.reactivex.Observable;

import id.govca.jetpackassignment.pojo.MovieList;
import id.govca.jetpackassignment.rest.ApiInterface;
import id.govca.jetpackassignment.rest.Constants;

public class RemoteRepository {
    private static RemoteRepository INSTANCE;
    private ApiInterface mApiInterface;

    private RemoteRepository(ApiInterface apiInterface){
        mApiInterface = apiInterface;
    }

    public static RemoteRepository getInstance(ApiInterface apiInterface) {
        if (INSTANCE == null) {
            INSTANCE = new RemoteRepository(apiInterface);
        }
        return INSTANCE;
    }

    public Observable<MovieList> getMovieList(String language){
        return mApiInterface.RxGetMovieList(Constants.API_KEY, language, 1);
    }

    public Observable<MovieDetail> getMovieDetail(int id_movie, String language){
        return mApiInterface.RxMovieDetails(id_movie, Constants.API_KEY, language);
    }

    public Observable<TVShowList> getTVShowList(String language){
        return mApiInterface.RxGetTVShowList(Constants.API_KEY, language);
    }

    public Observable<TVShowDetail> getTVShowDetail(int id_tv_show, String language){
        return mApiInterface.RxTVShowDetails(id_tv_show, Constants.API_KEY, language);
    }

    public LiveData<PagedList<Movie>> getMovieListLiveData(){
        RemoteDataSourceFactory remoteDataSourceFactory = new RemoteDataSourceFactory();
        LiveData<PageKeyedDataSource<Integer, Movie>> liveDataRemote = remoteDataSourceFactory.getMovieLiveDataSource();
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .build();
        return new LivePagedListBuilder<>(remoteDataSourceFactory, config).build();
    }
}
