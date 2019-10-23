package id.govca.jetpackassignment.data.source;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import id.govca.jetpackassignment.GlobalApplication;
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
                        Log.d(TAG, "onComplete");
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
    public LiveData<List<Favorite>> getFavorites(int type) {
        MutableLiveData<List<Favorite>> favoritesMutableLiveData = new MutableLiveData<>();

        localRepository.getFavorites(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Favorite>>() {
                    @Override
                    public void onNext(List<Favorite> favorites) {
                        favoritesMutableLiveData.postValue(favorites);
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

        return favoritesMutableLiveData;
    }

    @Override
    public LiveData<Favorite> getFavoriteDetail(int type, int id) {
        MutableLiveData<Favorite> favoriteMutableLiveData = new MutableLiveData<>();

        localRepository.getFavoriteDetail(type, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Favorite>() {
                    @Override
                    public void onNext(Favorite favorite) {
                        favoriteMutableLiveData.postValue(favorite);
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

        return favoriteMutableLiveData;
    }

    @Override
    public LiveData<Integer> checkFavorite(int type, int thingsId) {
        MutableLiveData<Integer> favoriteMutableLiveData = new MutableLiveData<>();

        localRepository.checkFavorite(type, thingsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        favoriteMutableLiveData.postValue(integer);
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

        return favoriteMutableLiveData;
    }

    @Override
    public LiveData<Integer> deleteFavorite(int type, int thingsId) {
        MutableLiveData<Integer> favoriteMutableLiveData = new MutableLiveData<>();

        localRepository.deleteFavorite(type, thingsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        favoriteMutableLiveData.postValue(integer);
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

        return favoriteMutableLiveData;
    }

    @Override
    public LiveData<Long> insertFavorite(Favorite favorite) {
        MutableLiveData<Long> favoriteMutableLiveData = new MutableLiveData<>();

        localRepository.insertFavorite(favorite)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long myLong) {
                        favoriteMutableLiveData.postValue(myLong);
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

        return favoriteMutableLiveData;
    }
}
