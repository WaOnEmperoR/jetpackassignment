package id.govca.jetpackassignment.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.List;

import id.govca.jetpackassignment.EspressoIdlingResource;
import id.govca.jetpackassignment.GlobalApplication;
import id.govca.jetpackassignment.data.source.MovieRepository;
import id.govca.jetpackassignment.pojo.Movie;
import id.govca.jetpackassignment.pojo.MovieDetail;
import id.govca.jetpackassignment.pojo.MovieList;
import id.govca.jetpackassignment.rest.ApiClient;
import id.govca.jetpackassignment.rest.ApiInterface;
import id.govca.jetpackassignment.rest.Constants;
import id.govca.jetpackassignment.rest.RxObservableSchedulers;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MovieViewModel extends ViewModel {
    private MutableLiveData<MovieDetail> movieDetail = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private final String TAG = this.getClass().getSimpleName();
    private MovieRepository movieRepository;

    Context context = GlobalApplication.getAppContext();

    public MutableLiveData<MovieDetail> getMovieDetail() {
        return movieDetail;
    }

    public void setMovieDetail(int id, String param_lang) {
        Log.d(TAG, "Calling Set Movie Detail");
        ObserveMovieDetail(id, param_lang);
    }

    public MovieViewModel(){}

    public LiveData<MovieDetail> getListMoviesLiveData(String param_lang, int movieId){
        return movieRepository.getMovieDetail(param_lang, movieId);
    }

    public MovieViewModel(MovieRepository movieRepository)
    {
        this.movieRepository = movieRepository;
    }

    public void fetchMovieDetail(){
        final ApiInterface mApiService = ApiClient.getClient().create(ApiInterface.class);

        disposable.add(
                mApiService.RxMovieDetails(475557, Constants.API_KEY, "en-US")
                    .compose(RxObservableSchedulers.TEST_SCHEDULER.applySchedulers())
                    .subscribe(this::onSuccess,
                            this::onError)
        );
    }

    private void onSuccess(MovieDetail movieDetail) {
        this.movieDetail.postValue(movieDetail);
    }

    private void onError(Throwable error) {
        return;
    }

    private Observable<MovieDetail> getMovieDetailObs(int idThings, String language){
        final ApiInterface mApiService = ApiClient.getClient().create(ApiInterface.class);

        return mApiService.RxMovieDetails(idThings, Constants.API_KEY, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void ObserveMovieDetail(int idThings, String param_lang)
    {
        Observable<MovieDetail> movieDetailObservable = getMovieDetailObs(idThings, param_lang);

        disposable.add(
                movieDetailObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<MovieDetail>() {
                            @Override
                            public void onNext(MovieDetail movieDetail) {
                                MovieViewModel.this.movieDetail.setValue(movieDetail);
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
