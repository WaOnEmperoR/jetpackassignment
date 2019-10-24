package id.govca.jetpackassignment.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.List;

import id.govca.jetpackassignment.EspressoIdlingResource;
import id.govca.jetpackassignment.GlobalApplication;
import id.govca.jetpackassignment.data.source.MovieRepository;
import id.govca.jetpackassignment.pojo.Movie;
import id.govca.jetpackassignment.pojo.MovieList;
import id.govca.jetpackassignment.pojo.TVShow;
import id.govca.jetpackassignment.pojo.TVShowList;
import id.govca.jetpackassignment.rest.ApiClient;
import id.govca.jetpackassignment.rest.ApiInterface;
import id.govca.jetpackassignment.rest.Constants;
import id.govca.jetpackassignment.rest.RxObservableSchedulers;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class TvShowListViewModel extends ViewModel {
    private MutableLiveData<TVShowList> listTvShows = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private final String TAG = this.getClass().getSimpleName();
    private MovieRepository movieRepository;

    Context context = GlobalApplication.getAppContext();

    public MutableLiveData<TVShowList> getListTvShows() {
        return listTvShows;
    }

    public TvShowListViewModel(){

    }

    public LiveData<PagedList<TVShow>> getPagedListMoviesLiveData(String language){
        return movieRepository.getTVShowsPaged(language);
    }

    public LiveData<List<TVShow>> getListTVShowLiveData(String param_lang){
        return movieRepository.getAllTVShows(param_lang);
    }

    public TvShowListViewModel(MovieRepository movieRepository)
    {
        this.movieRepository = movieRepository;
    }

    public void setListTvShows(String param_lang) {
        Log.d(TAG, "Calling Set List TV Shows");
        ObserveTVShow(param_lang);
    }

    public void setSearchTVShows(String query, String param_lang){
        Log.d(TAG, "Calling Set Search TV Shows");
        ObserveSearchTVShows(query, param_lang);
    }

    public void fetchTVShowList()
    {
        final ApiInterface mApiService = ApiClient.getClient().create(ApiInterface.class);

        disposable.add(
                mApiService.RxGetTVShowList(Constants.API_KEY, "en-US", 1)
                        .compose(RxObservableSchedulers.TEST_SCHEDULER.applySchedulers())
                        .subscribe(this::onSuccess,
                                this::onError)
        );
    }

    private void onSuccess(TVShowList tvShowList) {
        listTvShows.postValue(tvShowList);
    }

    private void onError(Throwable error) {
        return;
    }

    private Observable<TVShowList> getTVShowListObs(String param_lang)
    {
        final ApiInterface mApiService = ApiClient.getClient().create(ApiInterface.class);

        return mApiService.RxGetTVShowList(Constants.API_KEY, param_lang, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void ObserveTVShow (String param_lang)
    {
        Observable<TVShowList> tvShowListObservable = getTVShowListObs(param_lang);

        disposable.add(
                tvShowListObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableObserver<TVShowList>() {
                        @Override
                        public void onNext(TVShowList tvShowList) {
                            for (int i=0; i<tvShowList.getTvShowArrayList().size(); i++)
                            {
                                Log.d(TAG, tvShowList.getTvShowArrayList().get(i).getName());
                            }

                            listTvShows.setValue(tvShowList);
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

    private Observable<TVShowList> searchTVShowListObs(String query, String param_lang){
        final ApiInterface mApiService = ApiClient.getClient().create(ApiInterface.class);

        return mApiService.RxSearchTVShows(query, Constants.API_KEY, param_lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void ObserveSearchTVShows(String query, String param_lang)
    {
        Observable<TVShowList> tvShowListObservable = searchTVShowListObs(query, param_lang);

        disposable.add(
                tvShowListObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<TVShowList>() {
                            @Override
                            public void onNext(TVShowList tvShowList) {
                                for (int i=0; i<tvShowList.getTvShowArrayList().size(); i++)
                                {
                                    Log.d(TAG, tvShowList.getTvShowArrayList().get(i).getName());
                                }

                                listTvShows.setValue(tvShowList);
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
