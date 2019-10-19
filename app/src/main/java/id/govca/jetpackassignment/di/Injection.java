package id.govca.jetpackassignment.di;

import android.app.Application;

import id.govca.jetpackassignment.data.source.MovieRepository;
import id.govca.jetpackassignment.data.source.local.LocalRepository;
import id.govca.jetpackassignment.data.source.local.room.FavoriteDatabase;
import id.govca.jetpackassignment.data.source.remote.RemoteRepository;
import id.govca.jetpackassignment.rest.ApiClient;
import id.govca.jetpackassignment.rest.ApiInterface;
import id.govca.jetpackassignment.utils.AppExecutors;

public class Injection {
    public static MovieRepository provideRepository(Application application) {

        FavoriteDatabase favoriteDatabase = FavoriteDatabase.getInstance(application);

        ApiInterface mApiService = ApiClient.getClient().create(ApiInterface.class);

        RemoteRepository remoteRepository = RemoteRepository.getInstance(mApiService);
        LocalRepository localRepository = LocalRepository.getInstance(favoriteDatabase.favoriteDao());
        AppExecutors appExecutors = new AppExecutors();

        return MovieRepository.getInstance(remoteRepository, localRepository, appExecutors);
    }
}
