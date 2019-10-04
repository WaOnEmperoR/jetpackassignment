package id.govca.jetpackassignment.viewmodel;

import id.govca.jetpackassignment.base.BaseViewState;
import id.govca.jetpackassignment.pojo.MovieList;

public class MovieListViewState extends BaseViewState<MovieList> {
    private MovieListViewState(MovieList data, int currentState, Throwable error) {
        this.data = data;
        this.error = error;
        this.currentState = currentState;
    }

    public static MovieListViewState ERROR_STATE = new MovieListViewState(null, State.FAILED.value, new Throwable());
    public static MovieListViewState LOADING_STATE = new MovieListViewState(null, State.LOADING.value, null);
    public static MovieListViewState SUCCESS_STATE = new MovieListViewState(new MovieList(), State.SUCCESS.value, null);
}
