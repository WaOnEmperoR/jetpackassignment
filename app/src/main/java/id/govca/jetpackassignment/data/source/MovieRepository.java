package id.govca.jetpackassignment.data.source;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import id.govca.jetpackassignment.data.NetworkBoundResource;
import id.govca.jetpackassignment.data.source.local.LocalRepository;
import id.govca.jetpackassignment.data.source.local.entity.Favorite;
import id.govca.jetpackassignment.data.source.remote.ApiResponse;
import id.govca.jetpackassignment.data.source.remote.RemoteRepository;
import id.govca.jetpackassignment.pojo.Movie;
import id.govca.jetpackassignment.pojo.MovieDetail;
import id.govca.jetpackassignment.pojo.MovieList;
import id.govca.jetpackassignment.pojo.TVShow;
import id.govca.jetpackassignment.pojo.TVShowDetail;
import id.govca.jetpackassignment.pojo.TVShowList;
import id.govca.jetpackassignment.utils.AppExecutors;
import id.govca.jetpackassignment.vo.Resource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MovieRepository implements MovieDataSource {

    private volatile static MovieRepository INSTANCE = null;

    private final String TAG = this.getClass().getSimpleName();

    private final RemoteRepository remoteRepository;
    private final LocalRepository localRepository;
    private final AppExecutors appExecutors;

    private MovieRepository(@NonNull RemoteRepository remoteRepository, @NonNull LocalRepository localRepository, AppExecutors appExecutors){
        this.remoteRepository = remoteRepository;
        this.localRepository = localRepository;
        this.appExecutors = appExecutors;
    }

    public static MovieRepository getInstance( RemoteRepository remoteData, LocalRepository localData, AppExecutors appExecutors){
        if (INSTANCE == null) {
            synchronized (MovieRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieRepository(remoteData, localData, appExecutors);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<List<Movie>> getAllMovies(String param_lang) {
        MutableLiveData<List<Movie>> movieList = new MutableLiveData<>();

        remoteRepository.getMovieList(param_lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<MovieList, ArrayList<Movie>>() {
                    @Override
                    public ArrayList<Movie> apply(MovieList movieList) throws Exception {
                        return movieList.getMovieArrayList();
                    }
                })
                .subscribeWith(new Observer<ArrayList<Movie>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<Movie> movies) {
                        movieList.postValue(movies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                        return;
                    }
                });

        return movieList;
    }

    @Override
    public LiveData<MovieDetail> getMovieDetail(String param_lang, int movieId) {
        MutableLiveData<MovieDetail> myMovieDetail = new MutableLiveData<>();

        remoteRepository.getMovieDetail(movieId, param_lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<MovieDetail>() {
                    @Override
                    public void onNext(MovieDetail movieDetail) {
                        myMovieDetail.postValue(movieDetail);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });

        return myMovieDetail;
    }

    @Override
    public LiveData<List<TVShow>> getAllTVShows(String param_lang) {
        MutableLiveData<List<TVShow>> tvShowList = new MutableLiveData<>();

        remoteRepository.getTVShowList(param_lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<TVShowList, ArrayList<TVShow>>() {
                    @Override
                    public ArrayList<TVShow> apply(TVShowList tvShowList) throws Exception {
                        return tvShowList.getTvShowArrayList();
                    }
                })
                .subscribeWith(new DisposableObserver<ArrayList<TVShow>>() {
                    @Override
                    public void onNext(ArrayList<TVShow> tvShows) {
                        tvShowList.postValue(tvShows);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
                
        return tvShowList;
    }

    @Override
    public LiveData<TVShowDetail> getTVShowDetail(String param_lang, int tvShowId) {
        MutableLiveData<TVShowDetail> tvShowDetailMutableLiveData = new MutableLiveData<>();

        remoteRepository.getTVShowDetail(tvShowId, param_lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<TVShowDetail>() {
                    @Override
                    public void onNext(TVShowDetail tvShowDetail) {
                        tvShowDetailMutableLiveData.postValue(tvShowDetail);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });

        return tvShowDetailMutableLiveData;
    }

    @Override
    public LiveData<Resource<List<Favorite>>> getFavorites(int type) {
        return new NetworkBoundResource<List<Favorite>, List<Favorite>>(appExecutors) {
            @Override
            protected LiveData<List<Favorite>> loadFromDB() {
                return localRepository.getFavorites(type);
            }

            @Override
            protected Boolean shouldFetch(List<Favorite> data) {
                return false;
            }

            @Override
            protected LiveData<ApiResponse<List<Favorite>>> createCall() {
                return null;
            }

            @Override
            protected void saveCallResult(List<Favorite> data) {

            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<Favorite>> getFavoriteDetail(int type, int id) {
        return null;
    }

    @Override
    public int checkFavorite(int type, int thingsId) {
        return 0;
    }

    @Override
    public void deleteFavorite(int type, int thingsId) {

    }

    @Override
    public Long insertFavorite(Favorite favorite) {
        return null;
    }
}
