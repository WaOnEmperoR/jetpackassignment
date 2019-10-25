package id.govca.jetpackassignment.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.MockitoAnnotations;

import id.govca.jetpackassignment.data.source.MovieRepository;
import id.govca.jetpackassignment.data.source.local.entity.Favorite;
import id.govca.jetpackassignment.pojo.Movie;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FavoriteListViewModelTest {
    private FavoriteListViewModel favoriteListViewModelPaged;
    private MovieRepository movieRepository = mock(MovieRepository.class);

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

        favoriteListViewModelPaged = new FavoriteListViewModel(movieRepository);
    }

    @Test
    public void testPaginationFavoriteMovie(){
        MutableLiveData<PagedList<Favorite>> dummyFavorites = new MutableLiveData<>();
        PagedList<Favorite> pagedList = mock(PagedList.class);

        dummyFavorites.setValue(pagedList);

        when(movieRepository.getFavoritesPaged(0)).thenReturn(dummyFavorites);

        Observer<PagedList<Favorite>> observer = mock(Observer.class);

        favoriteListViewModelPaged.getAllFavorites(0).observeForever(observer);

        verify(observer).onChanged(pagedList);
    }

    @Test
    public void testPaginationFavoriteTVShow(){
        MutableLiveData<PagedList<Favorite>> dummyFavorites = new MutableLiveData<>();
        PagedList<Favorite> pagedList = mock(PagedList.class);

        dummyFavorites.setValue(pagedList);

        when(movieRepository.getFavoritesPaged(1)).thenReturn(dummyFavorites);

        Observer<PagedList<Favorite>> observer = mock(Observer.class);

        favoriteListViewModelPaged.getAllFavorites(1).observeForever(observer);

        verify(observer).onChanged(pagedList);
    }
}
