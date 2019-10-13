package id.govca.jetpackassignment.data.source;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import id.govca.jetpackassignment.pojo.Movie;
import id.govca.jetpackassignment.pojo.MovieDetail;
import id.govca.jetpackassignment.pojo.MovieList;
import id.govca.jetpackassignment.pojo.TVShow;
import id.govca.jetpackassignment.pojo.TVShowDetail;
import id.govca.jetpackassignment.pojo.TVShowList;
import id.govca.jetpackassignment.rest.ApiClient;
import id.govca.jetpackassignment.rest.ApiInterface;
import id.govca.jetpackassignment.rest.Constants;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MovieRepository implements MovieDataSource {

    private volatile static MovieRepository INSTANCE = null;

    private final String TAG = this.getClass().getSimpleName();

    private MovieRepository(){

    }

    public static MovieRepository getInstance(){
        if (INSTANCE == null) {
            synchronized (MovieRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieRepository();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<List<Movie>> getAllMovies(String param_lang) {
        final ApiInterface mApiService = ApiClient.getClient().create(ApiInterface.class);
        MutableLiveData<List<Movie>> movieList = new MutableLiveData<>();

        mApiService.RxGetMovieList(Constants.API_KEY, param_lang)
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
        final ApiInterface mApiService = ApiClient.getClient().create(ApiInterface.class);
        MutableLiveData<MovieDetail> myMovieDetail = new MutableLiveData<>();

        mApiService.RxMovieDetails(movieId, Constants.API_KEY, param_lang)
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
        final ApiInterface mApiService = ApiClient.getClient().create(ApiInterface.class);
        MutableLiveData<List<TVShow>> tvShowList = new MutableLiveData<>();

        mApiService.RxGetTVShowList(Constants.API_KEY, param_lang)
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
        final ApiInterface mApiService = ApiClient.getClient().create(ApiInterface.class);
        MutableLiveData<TVShowDetail> tvShowDetailMutableLiveData = new MutableLiveData<>();

        mApiService.RxTVShowDetails(tvShowId, Constants.API_KEY, param_lang)
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
}
