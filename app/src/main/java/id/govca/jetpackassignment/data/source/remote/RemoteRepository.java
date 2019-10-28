package id.govca.jetpackassignment.data.source.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import id.govca.jetpackassignment.pojo.Movie;
import id.govca.jetpackassignment.pojo.MovieDetail;
import id.govca.jetpackassignment.pojo.TVShow;
import id.govca.jetpackassignment.pojo.TVShowDetail;
import id.govca.jetpackassignment.pojo.TVShowList;
import io.reactivex.Observable;

import id.govca.jetpackassignment.pojo.MovieList;
import id.govca.jetpackassignment.rest.ApiInterface;
import id.govca.jetpackassignment.rest.Constants;

public class RemoteRepository {
    private static RemoteRepository INSTANCE;
    private ApiInterface mApiInterface;

    private LiveData<String> progressLoadStatus = new MutableLiveData<>();

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
        return mApiInterface.RxGetTVShowList(Constants.API_KEY, language, 1);
    }

    public Observable<TVShowDetail> getTVShowDetail(int id_tv_show, String language){
        return mApiInterface.RxTVShowDetails(id_tv_show, Constants.API_KEY, language);
    }

    public LiveData<PagedList<Movie>> getMovieListLiveData(String language, RemoteDataSourceMovieFactory remoteDataSourceMovieFactory){
//        RemoteDataSourceMovieFactory rmFactory = new RemoteDataSourceMovieFactory(language);
        remoteDataSourceMovieFactory.setLanguage(language);

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(5)
                .build();

        return new LivePagedListBuilder<>(remoteDataSourceMovieFactory, config).build();
    }

    public LiveData<PagedList<TVShow>> getTVShowListLiveData(String language){
        RemoteDataSourceTVShowFactory remoteDataSourceTVShowFactory = new RemoteDataSourceTVShowFactory(language);
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(5)
                .build();
        return new LivePagedListBuilder<>(remoteDataSourceTVShowFactory, config).build();
    }
}
