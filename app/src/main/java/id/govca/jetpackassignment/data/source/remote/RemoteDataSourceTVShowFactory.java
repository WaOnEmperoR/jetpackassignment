package id.govca.jetpackassignment.data.source.remote;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import id.govca.jetpackassignment.pojo.Movie;
import id.govca.jetpackassignment.pojo.TVShow;

public class RemoteDataSourceTVShowFactory extends DataSource.Factory {
    private final String language;

    //creating the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, TVShow>> tvShowLiveDataSource = new MutableLiveData<>();

    public RemoteDataSourceTVShowFactory(String mLanguage){
        language = mLanguage;
    }

    @NonNull
    @Override
    public DataSource create() {
        //getting our data source object
        RemoteDataSourceTVShow remoteDataSourceTVShow = new RemoteDataSourceTVShow(language);

        //posting the datasource to get the values
        tvShowLiveDataSource.postValue(remoteDataSourceTVShow);

        return remoteDataSourceTVShow;
    }

    //getter for itemlivedatasource
    public MutableLiveData<PageKeyedDataSource<Integer, TVShow>> getMovieLiveDataSource() {
        return tvShowLiveDataSource;
    }
}
