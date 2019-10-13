package id.govca.jetpackassignment.utils;

import java.util.ArrayList;

import id.govca.jetpackassignment.pojo.Movie;
import id.govca.jetpackassignment.pojo.MovieList;
import id.govca.jetpackassignment.rest.ApiClient;
import id.govca.jetpackassignment.rest.ApiInterface;
import id.govca.jetpackassignment.rest.Constants;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class DataFetcher {
    private CompositeDisposable disposable = new CompositeDisposable();
    private final String TAG = this.getClass().getSimpleName();

    public DataFetcher(){

    }

    public ArrayList<Movie> getMovieList(String param_lang){
        final ApiInterface mApiService = ApiClient.getClient().create(ApiInterface.class);
        final ArrayList<Movie> movieArrayList = new ArrayList<Movie>();

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
                        movieArrayList.addAll(movies);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        return;
                    }
                });

        return movieArrayList;
    }
}
