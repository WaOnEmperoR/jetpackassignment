package id.govca.jetpackassignment.data.source;

import id.govca.jetpackassignment.pojo.MovieDetail;
import id.govca.jetpackassignment.pojo.MovieList;
import id.govca.jetpackassignment.pojo.TVShowDetail;
import id.govca.jetpackassignment.pojo.TVShowList;
import id.govca.jetpackassignment.rest.Constants;
import io.reactivex.Observable;

public class FakeRemoteRepository {
    private static FakeRemoteRepository INSTANCE;

    private FakeRemoteRepository(){}

    public static FakeRemoteRepository getInstance(){
        if (INSTANCE == null)
        {
            INSTANCE = new FakeRemoteRepository();
        }

        return INSTANCE;
    }

    public MovieList getMovieList(){
        return null;
    }

    public MovieDetail getMovieDetail(int id_movie){
        return null;
    }

    public TVShowList getTVShowList(){
        return null;
    }

    public TVShowDetail getTVShowDetail(int id_tv_show){
        return null;
    }
}
