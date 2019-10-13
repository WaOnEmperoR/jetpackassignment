package id.govca.jetpackassignment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import id.govca.jetpackassignment.data.source.MovieRepository;
import id.govca.jetpackassignment.di.Injection;
import id.govca.jetpackassignment.pojo.Movie;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;

    private MovieRepository mMovieRepository;

    private ViewModelFactory(MovieRepository movieRepository) {
        mMovieRepository = movieRepository;
    }

    private ViewModelFactory() {

    }

    public static ViewModelFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(Injection.provideRepository());
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(MovieListViewModel.class)) {
            //noinspection unchecked
            return (T) new MovieListViewModel(mMovieRepository);
        } else if (modelClass.isAssignableFrom(MovieViewModel.class)) {
            //noinspection unchecked
            return (T) new MovieViewModel();
        } else if (modelClass.isAssignableFrom(TvShowListViewModel.class)) {
            //noinspection unchecked
            return (T) new TvShowListViewModel();
        } else if (modelClass.isAssignableFrom(TvShowViewModel.class)) {
            //noinspection unchecked
            return (T) new TvShowViewModel();
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
