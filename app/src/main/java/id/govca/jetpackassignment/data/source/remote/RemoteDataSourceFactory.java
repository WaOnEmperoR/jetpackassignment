package id.govca.jetpackassignment.data.source.remote;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import id.govca.jetpackassignment.pojo.Movie;

public class RemoteDataSourceFactory extends DataSource.Factory {

    //creating the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, Movie>> movieLiveDataSource = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource<Integer, Movie> create() {
        //getting our data source object
        RemoteDataSource remoteDataSource = new RemoteDataSource();

        //posting the datasource to get the values
        movieLiveDataSource.postValue(remoteDataSource);

        return remoteDataSource;
    }

    //getter for itemlivedatasource
    public MutableLiveData<PageKeyedDataSource<Integer, Movie>> getMovieLiveDataSource() {
        return movieLiveDataSource;
    }
}
