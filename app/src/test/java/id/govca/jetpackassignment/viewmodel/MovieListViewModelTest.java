package id.govca.jetpackassignment.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import id.govca.jetpackassignment.GlobalApplication;
import id.govca.jetpackassignment.fragment.MovieFragment;
import id.govca.jetpackassignment.pojo.Movie;
import id.govca.jetpackassignment.pojo.MovieList;
import id.govca.jetpackassignment.rest.ApiClient;
import id.govca.jetpackassignment.rest.ApiInterface;
import id.govca.jetpackassignment.rest.Constants;
import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieListViewModelTest {
    private MovieListViewModel movieListViewModel;

//    @Mock
//    ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);
    @Mock
    Observer<MovieList> observer;
    @Mock
    LifecycleOwner lifecycleOwner;
    Lifecycle lifecycle;

    @BeforeClass
    public static void setUpClass() {

        // Override the default "out of the box" AndroidSchedulers.mainThread() Scheduler
        //
        // This is necessary here because otherwise if the static initialization block in AndroidSchedulers
        // is executed before this, then the Android SDK dependent version will be provided instead.
        //
        // This would cause a java.lang.ExceptionInInitializerError when running the test as a
        // Java JUnit test as any attempt to resolve the default underlying implementation of the
        // AndroidSchedulers.mainThread() will fail as it relies on unavailable Android dependencies.

        // Comment out this line to see the java.lang.ExceptionInInitializerError
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        lifecycle = new LifecycleRegistry(lifecycleOwner);
        movieListViewModel = new MovieListViewModel();
        movieListViewModel.getListMovies().observeForever(observer);
    }

    @Test
    public void testNull() {
        movieListViewModel.fetchMovieList();
        ArgumentCaptor<MovieList> movieListCaptor = ArgumentCaptor.forClass(MovieList.class);

        verify(observer, times(1)).onChanged(movieListCaptor.capture());

        List<MovieList> capturedMovieList = movieListCaptor.getAllValues();
        assertNotNull(capturedMovieList.get(0).getMovieArrayList());
        assertEquals(capturedMovieList.get(0).getMovieArrayList().size(), 20);

//        when(mApiInterface.RxGetMovieList(Constants.API_KEY, "en-US")).thenReturn(null);
//        movieListViewModel.fetchMovieList();
//        assertNotNull(movieListViewModel.getListMovies().getValue().getMovieArrayList());
//        assertEquals(20, movieListViewModel.getListMovies().getValue().getMovieArrayList().size());
    }

}