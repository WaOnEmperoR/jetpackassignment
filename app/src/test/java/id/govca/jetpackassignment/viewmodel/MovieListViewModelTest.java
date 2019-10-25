package id.govca.jetpackassignment.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import id.govca.jetpackassignment.data.source.MovieRepository;
import id.govca.jetpackassignment.pojo.Movie;
import id.govca.jetpackassignment.pojo.MovieList;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieListViewModelTest {
    private MovieListViewModel movieListViewModel;
    private MovieListViewModel movieListViewModelPaged;

    private MovieRepository movieRepository = mock(MovieRepository.class);

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

        movieListViewModelPaged = new MovieListViewModel(movieRepository);
    }

    @Test
    public void testAPICall() {
        movieListViewModel.fetchMovieList();
        ArgumentCaptor<MovieList> movieListCaptor = ArgumentCaptor.forClass(MovieList.class);

        verify(observer, times(1)).onChanged(movieListCaptor.capture());

        List<MovieList> capturedMovieList = movieListCaptor.getAllValues();
        assertNotNull(capturedMovieList.get(0).getMovieArrayList());
        assertEquals(capturedMovieList.get(0).getMovieArrayList().size(), 20);
    }

    @Test
    public void testPagination(){
        MutableLiveData<PagedList<Movie>> dummyMovies = new MutableLiveData<>();
        PagedList<Movie> pagedList = mock(PagedList.class);

        dummyMovies.setValue(pagedList);

        when(movieRepository.getMoviesPaged("en-US")).thenReturn(dummyMovies);

        Observer<PagedList<Movie>> observer = mock(Observer.class);

        movieListViewModelPaged.getPagedListMoviesLiveData("en-US").observeForever(observer);

        verify(observer).onChanged(pagedList);
    }

}