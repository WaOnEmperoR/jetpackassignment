package id.govca.jetpackassignment.data.source.remote;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import id.govca.jetpackassignment.data.source.MovieRepository;
import id.govca.jetpackassignment.pojo.Movie;

public class RemoteDataSourceMovieFactory extends DataSource.Factory {

    private String language = "en-US";
    private final MovieRepository movieRepository;

    private final String TAG = this.getClass().getSimpleName();

    //creating the mutable live data
    private MutableLiveData<RemoteDataSourceMovie> movieLiveDataSource = new MutableLiveData<>();

    public RemoteDataSourceMovieFactory(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    @NonNull
    @Override
    public DataSource<Integer, Movie> create() {
        //getting our data source object
        RemoteDataSourceMovie remoteDataSourceMovie = new RemoteDataSourceMovie(getLanguage(), movieRepository);

        //posting the datasource to get the values
        movieLiveDataSource.postValue(remoteDataSourceMovie);

        return remoteDataSourceMovie;
    }

    //getter for itemlivedatasource
    public MutableLiveData<RemoteDataSourceMovie> getMovieLiveDataSource() {
        return movieLiveDataSource;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
