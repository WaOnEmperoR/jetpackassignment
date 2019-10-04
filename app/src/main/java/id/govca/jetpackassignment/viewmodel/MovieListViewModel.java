package id.govca.jetpackassignment.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import id.govca.jetpackassignment.GlobalApplication;
import id.govca.jetpackassignment.pojo.MovieList;
import id.govca.jetpackassignment.rest.ApiClient;
import id.govca.jetpackassignment.rest.ApiInterface;
import id.govca.jetpackassignment.rest.Constants;
import id.govca.jetpackassignment.rest.RxObservableSchedulers;
import id.govca.jetpackassignment.rest.RxSingleSchedulers;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MovieListViewModel extends ViewModel {
    private MutableLiveData<MovieList> listMovies = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private final String TAG = this.getClass().getSimpleName();
//    private final ApiInterface apiInterface;

    Context context = GlobalApplication.getAppContext();

    public MutableLiveData<MovieList> getListMovies() {
        return listMovies;
    }

    public MovieListViewModel()
    {
//        this.apiInterface = apiInterface;
    }

    public void setListMovies(String param_lang) {
        Log.d(TAG, "Calling Set List Movies");
        ObserveMovie(param_lang);
    }

    public void setSearchMovies(String query, String param_lang){
        Log.d(TAG, "Calling Set Search Movies");
        ObserveSearchMovie(query, param_lang);
    }

    public void fetchMovieList()
    {
        final ApiInterface mApiService = ApiClient.getClient().create(ApiInterface.class);

        disposable.add(
            mApiService.RxGetMovieList(Constants.API_KEY, "en-US")
                .compose(RxObservableSchedulers.TEST_SCHEDULER.applySchedulers())
                .subscribe(this::onSuccess,
                        this::onError)

        );
    }

    private void onSuccess(MovieList movieList) {
        listMovies.postValue(movieList);
    }

    private void onError(Throwable error) {
        return;
    }

    private Observable<MovieList> getMovieListObs(String param_lang){
        final ApiInterface mApiService = ApiClient.getClient().create(ApiInterface.class);

        return mApiService.RxGetMovieList(Constants.API_KEY, param_lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void ObserveMovie(String param_lang)
    {
        Observable<MovieList> movieListObservable = getMovieListObs(param_lang);

        disposable.add(
                movieListObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<MovieList>() {
                            @Override
                            public void onNext(MovieList movieList) {
                                for (int i=0; i<movieList.getMovieArrayList().size(); i++)
                                {
                                    Log.d(TAG, movieList.getMovieArrayList().get(i).getTitle());
                                }

                                listMovies.setValue(movieList);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Observable error : " + e.getMessage());
                                DynamicToast.makeError(context, e.getMessage(), 5).show();
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "onComplete from RxJava");
                                DynamicToast.makeSuccess(context, "Finished Loading Data", 3).show();
                                this.dispose();
                            }
                        })
        );
    }

    private Observable<MovieList> searchMovieListObs(String query, String param_lang){
        final ApiInterface mApiService = ApiClient.getClient().create(ApiInterface.class);

        return mApiService.RxSearchMovies(query, Constants.API_KEY, param_lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void ObserveSearchMovie(String query, String param_lang)
    {
        Observable<MovieList> movieListObservable = searchMovieListObs(query, param_lang);

        disposable.add(
                movieListObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<MovieList>() {
                            @Override
                            public void onNext(MovieList movieList) {
                                for (int i=0; i<movieList.getMovieArrayList().size(); i++)
                                {
                                    Log.d(TAG, movieList.getMovieArrayList().get(i).getTitle());
                                }

                                listMovies.setValue(movieList);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Observable error : " + e.getMessage());
                                DynamicToast.makeError(context, e.getMessage(), 5).show();
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "onComplete from RxJava");
                                DynamicToast.makeSuccess(context, "Finished Loading Data", 3).show();
                                this.dispose();
                            }
                        })
        );
    }
}
