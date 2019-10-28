package id.govca.jetpackassignment.data.source.remote;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import java.util.ArrayList;

import id.govca.jetpackassignment.data.source.MovieRepository;
import id.govca.jetpackassignment.pojo.Movie;
import id.govca.jetpackassignment.pojo.MovieList;
import id.govca.jetpackassignment.rest.ApiClient;
import id.govca.jetpackassignment.rest.ApiInterface;
import id.govca.jetpackassignment.rest.Constants;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RemoteDataSourceMovie extends PageKeyedDataSource<Integer, Movie> {
    private ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

    //we will start from the first page which is 1
    private static final int FIRST_PAGE = 1;

    private final String language;
    private final MovieRepository movieRepository;

    private final String TAG = this.getClass().getSimpleName();
    private MutableLiveData<String> progressLiveStatus = new MutableLiveData<String>();

    public RemoteDataSourceMovie(String mLanguage, MovieRepository movieRepository){
        this.language = mLanguage;
        this.movieRepository = movieRepository;
    }

    public MutableLiveData<String> getProgressLiveStatus() {
        return progressLiveStatus;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movie> callback) {
        mApiInterface.RxGetMovieList(Constants.API_KEY, language, FIRST_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    progressLiveStatus.postValue(Constants.LOADING);
                })
                .map(new Function<MovieList, ArrayList<Movie>>() {
                    @Override
                    public ArrayList<Movie> apply(MovieList movieList) throws Exception {
                        return movieList.getMovieArrayList();
                    }
                })
                .subscribeWith(new DisposableObserver<ArrayList<Movie>>() {
                    @Override
                    public void onNext(ArrayList<Movie> movies) {
                        callback.onResult(movies, null, FIRST_PAGE + 1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressLiveStatus.postValue(Constants.LOADED);
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        progressLiveStatus.postValue(Constants.LOADED);
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
        mApiInterface.RxGetMovieList(Constants.API_KEY, language, params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    progressLiveStatus.postValue(Constants.LOADING);
                })
                .map(new Function<MovieList, ArrayList<Movie>>() {
                    @Override
                    public ArrayList<Movie> apply(MovieList movieList) throws Exception {
                        return movieList.getMovieArrayList();
                    }
                })
                .subscribeWith(new DisposableObserver<ArrayList<Movie>>() {
                    @Override
                    public void onNext(ArrayList<Movie> movies) {
                        Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;

                        callback.onResult(movies, adjacentKey);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressLiveStatus.postValue(Constants.LOADED);
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        progressLiveStatus.postValue(Constants.LOADED);
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
        mApiInterface.RxGetMovieList(Constants.API_KEY, language, params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    progressLiveStatus.postValue(Constants.LOADING);
                })
                .map(new Function<MovieList, ArrayList<Movie>>() {
                    @Override
                    public ArrayList<Movie> apply(MovieList movieList) throws Exception {
                        return movieList.getMovieArrayList();
                    }
                })
                .subscribeWith(new DisposableObserver<ArrayList<Movie>>() {
                    @Override
                    public void onNext(ArrayList<Movie> movies) {
                        if (movies.size() > 0)
                        {
                            Integer adjacentKey = params.key + 1;

                            callback.onResult(movies, adjacentKey);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressLiveStatus.postValue(Constants.LOADED);
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        progressLiveStatus.postValue(Constants.LOADED);
                        Log.d(TAG, "onComplete");
                    }
                });
    }
}
