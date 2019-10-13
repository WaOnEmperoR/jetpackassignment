package id.govca.jetpackassignment.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import id.govca.jetpackassignment.GlobalApplication;
import id.govca.jetpackassignment.data.source.MovieRepository;
import id.govca.jetpackassignment.pojo.MovieDetail;
import id.govca.jetpackassignment.pojo.TVShowDetail;
import id.govca.jetpackassignment.rest.ApiClient;
import id.govca.jetpackassignment.rest.ApiInterface;
import id.govca.jetpackassignment.rest.Constants;
import id.govca.jetpackassignment.rest.RxObservableSchedulers;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class TvShowViewModel extends ViewModel {
    private MutableLiveData<TVShowDetail> tvShowDetailMutableLiveData = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private final String TAG = this.getClass().getSimpleName();
    private MovieRepository movieRepository;

    Context context = GlobalApplication.getAppContext();

    public MutableLiveData<TVShowDetail> getTvShowDetail() {
        return tvShowDetailMutableLiveData;
    }

    public TvShowViewModel(){}

    public LiveData<TVShowDetail> getTvShowLiveData(String param_lang, int movieId){
        return movieRepository.getTVShowDetail(param_lang, movieId);
    }

    public TvShowViewModel(MovieRepository movieRepository)
    {
        this.movieRepository = movieRepository;
    }

    public void setTvShowDetail(int id, String param_lang) {
        Log.d(TAG, "Calling Set Movie Detail");
        ObserveTVShowDetail(id, param_lang);
    }

    private Observable<TVShowDetail> getTVShowDetailObs(int idThings, String language){
        final ApiInterface mApiService = ApiClient.getClient().create(ApiInterface.class);

        return mApiService.RxTVShowDetails(idThings, Constants.API_KEY, language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void fetchTvShowDetail(){
        final ApiInterface mApiService = ApiClient.getClient().create(ApiInterface.class);

        disposable.add(
                mApiService.RxTVShowDetails(456, Constants.API_KEY, "en-US")
                        .compose(RxObservableSchedulers.TEST_SCHEDULER.applySchedulers())
                        .subscribe(this::onSuccess,
                                this::onError)
        );
    }

    private void onSuccess(TVShowDetail tvShowDetail){
        tvShowDetailMutableLiveData.postValue(tvShowDetail);
    }

    private void onError(Throwable error) {
        return;
    }

    private void ObserveTVShowDetail(int idThings, String param_lang) {
        Observable<TVShowDetail> tvShowDetailObservable = getTVShowDetailObs(idThings, param_lang);

        disposable.add(
                tvShowDetailObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<TVShowDetail>() {
                        @Override
                        public void onNext(TVShowDetail tvShowDetail) {
                            tvShowDetailMutableLiveData.setValue(tvShowDetail);
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
