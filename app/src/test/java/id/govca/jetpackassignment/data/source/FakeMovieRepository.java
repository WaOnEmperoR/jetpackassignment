package id.govca.jetpackassignment.data.source;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import id.govca.jetpackassignment.data.source.remote.RemoteRepository;
import id.govca.jetpackassignment.pojo.Movie;
import id.govca.jetpackassignment.pojo.MovieDetail;
import id.govca.jetpackassignment.pojo.MovieList;
import id.govca.jetpackassignment.pojo.TVShow;
import id.govca.jetpackassignment.pojo.TVShowDetail;

public class FakeMovieRepository implements FakeMovieDataSource {
    private volatile static FakeMovieRepository INSTANCE = null;

    private final String TAG = this.getClass().getSimpleName();

    private final FakeRemoteRepository remoteRepository;

    FakeMovieRepository(@NonNull FakeRemoteRepository remoteRepository){
        this.remoteRepository = remoteRepository;
    }

    public static FakeMovieRepository getInstance( FakeRemoteRepository remoteData){
        if (INSTANCE == null) {
            synchronized (MovieRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FakeMovieRepository(remoteData);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public List<Movie> getAllMovies() {
        return remoteRepository.getMovieList().getMovieArrayList();
    }

    @Override
    public MovieDetail getMovieDetail(int movieId) {
        return remoteRepository.getMovieDetail(movieId);
    }

    @Override
    public List<TVShow> getAllTVShows() {
        return remoteRepository.getTVShowList().getTvShowArrayList();
    }

    @Override
    public TVShowDetail getTVShowDetail(int tvShowId) {
        return remoteRepository.getTVShowDetail(tvShowId);
    }
}
