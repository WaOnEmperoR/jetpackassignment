package id.govca.jetpackassignment.di;

import id.govca.jetpackassignment.data.source.MovieRepository;
import id.govca.jetpackassignment.data.source.remote.RemoteRepository;
import id.govca.jetpackassignment.rest.ApiClient;
import id.govca.jetpackassignment.rest.ApiInterface;

public class Injection {
    public static MovieRepository provideRepository() {

        ApiInterface mApiService = ApiClient.getClient().create(ApiInterface.class);

        RemoteRepository remoteRepository = RemoteRepository.getInstance(mApiService);

        return MovieRepository.getInstance(remoteRepository);
    }
}
