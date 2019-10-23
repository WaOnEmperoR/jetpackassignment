package id.govca.jetpackassignment.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.Callable;

import id.govca.jetpackassignment.GlobalApplication;
import id.govca.jetpackassignment.data.source.MovieRepository;
import id.govca.jetpackassignment.data.source.local.entity.Favorite;
import id.govca.jetpackassignment.data.source.local.room.FavoriteDatabase;
import id.govca.jetpackassignment.pojo.MovieList;
import id.govca.jetpackassignment.rest.ApiClient;
import id.govca.jetpackassignment.rest.ApiInterface;
import id.govca.jetpackassignment.rest.Constants;
import id.govca.jetpackassignment.rest.RxObservableSchedulers;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class FavoriteListViewModel extends ViewModel {
    private MutableLiveData<List<Favorite>> listFavorite = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private final String TAG = this.getClass().getSimpleName();
    private MovieRepository movieRepository;

    public MutableLiveData<List<Favorite>> getListFavorite() {
        return listFavorite;
    }

    public LiveData<List<Favorite>> getListFavoritesLiveData(int type){
        return movieRepository.getFavorites(type);
    }

    public FavoriteListViewModel(MovieRepository movieRepository)
    {
        this.movieRepository = movieRepository;
    }

    public void fetchFavoriteList(int type, Context localContext)
    {
        Observable<List<Favorite>> favoriteList = Observable.fromCallable(new Callable<List<Favorite>>() {
            @Override
            public List<Favorite> call() {
                return FavoriteDatabase.getInstance(localContext)
                        .favoriteDao()
                        .getFavorites(type);
            }
        });

        disposable.add(
                favoriteList
                        .compose(RxObservableSchedulers.TEST_SCHEDULER.applySchedulers())
                        .subscribe(this::onSuccess,
                                this::onError)
        );
    }

    private void onSuccess(List<Favorite> favoriteList) {
        listFavorite.postValue(favoriteList);
    }

    private void onError(Throwable error) {
        return;
    }
}
