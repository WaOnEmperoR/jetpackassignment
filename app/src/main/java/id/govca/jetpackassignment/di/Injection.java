package id.govca.jetpackassignment.di;

import id.govca.jetpackassignment.data.source.MovieRepository;

public class Injection {
    public static MovieRepository provideRepository() {

        return MovieRepository.getInstance();
    }
}
