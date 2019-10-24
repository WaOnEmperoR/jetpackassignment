package id.govca.jetpackassignment.data.source.remote;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import id.govca.jetpackassignment.pojo.Movie;

public class RemoteDataSourceMovieFactory extends DataSource.Factory {

    private final String language;

    //creating the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, Movie>> movieLiveDataSource = new MutableLiveData<>();

    public RemoteDataSourceMovieFactory(String mLanguage){
        language = mLanguage;
    }

    @NonNull
    @Override
    public DataSource<Integer, Movie> create() {
        //getting our data source object
        RemoteDataSourceMovie remoteDataSourceMovie = new RemoteDataSourceMovie(language);

        //posting the datasource to get the values
        movieLiveDataSource.postValue(remoteDataSourceMovie);

        return remoteDataSourceMovie;
    }

    //getter for itemlivedatasource
    public MutableLiveData<PageKeyedDataSource<Integer, Movie>> getMovieLiveDataSource() {
        return movieLiveDataSource;
    }
}
