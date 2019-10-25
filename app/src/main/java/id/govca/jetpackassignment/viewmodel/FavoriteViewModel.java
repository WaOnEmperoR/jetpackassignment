package id.govca.jetpackassignment.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import id.govca.jetpackassignment.data.source.MovieRepository;
import id.govca.jetpackassignment.data.source.local.entity.Favorite;
import io.reactivex.disposables.CompositeDisposable;

public class FavoriteViewModel extends ViewModel {
    private MutableLiveData<Favorite> favoriteMutableLiveData = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private final String TAG = this.getClass().getSimpleName();
    private MovieRepository movieRepository;

    public MutableLiveData<Favorite> getFavorite() {
        return favoriteMutableLiveData;
    }

    public LiveData<Favorite> getFavoriteLiveData(int type, int id){
        return movieRepository.getFavoriteDetail(type, id);
    }

    public FavoriteViewModel(MovieRepository movieRepository)
    {
        this.movieRepository = movieRepository;
    }

    public LiveData<Long> insertFavorite(Favorite favorite)
    {
        return movieRepository.insertFavorite(favorite);
    }

    public LiveData<Integer> checkFavorite(int type, int id)
    {
        return movieRepository.checkFavorite(type, id);
    }

    public LiveData<Integer> deleteFavorite(int type, int id)
    {
        return movieRepository.deleteFavorite(type, id);
    }

    public void deleteFavoritePaged(int type, int id)
    {
        movieRepository.deleteFavoritePaged(type, id);
    }
}
