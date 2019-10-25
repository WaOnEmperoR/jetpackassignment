package id.govca.jetpackassignment.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import id.govca.jetpackassignment.data.source.MovieRepository;
import id.govca.jetpackassignment.pojo.Movie;
import id.govca.jetpackassignment.pojo.TVShow;
import id.govca.jetpackassignment.pojo.TVShowList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TvShowListViewModelTest {
    private TvShowListViewModel tvShowListViewModel;

    private TvShowListViewModel tvShowListViewModelPaged;

    private MovieRepository movieRepository = mock(MovieRepository.class);

    @Mock
    Observer<TVShowList> observer;
    @Mock
    LifecycleOwner lifecycleOwner;
    Lifecycle lifecycle;

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        lifecycle = new LifecycleRegistry(lifecycleOwner);
        tvShowListViewModel = new TvShowListViewModel();
        tvShowListViewModel.getListTvShows().observeForever(observer);

        tvShowListViewModelPaged = new TvShowListViewModel(movieRepository);
    }

    @Test
    public void testAPICall() {
        tvShowListViewModel.fetchTVShowList();
        ArgumentCaptor<TVShowList> tvShowListCaptor = ArgumentCaptor.forClass(TVShowList.class);

        verify(observer, times(1)).onChanged(tvShowListCaptor.capture());

        List<TVShowList> capturedTVShowList = tvShowListCaptor.getAllValues();
        assertNotNull(capturedTVShowList.get(0).getTvShowArrayList());
        assertEquals(capturedTVShowList.get(0).getTvShowArrayList().size(), 20);
    }

    @Test
    public void testPagination(){
        MutableLiveData<PagedList<TVShow>> dummyTVShows = new MutableLiveData<>();
        PagedList<TVShow> pagedList = mock(PagedList.class);

        dummyTVShows.setValue(pagedList);

        when(movieRepository.getTVShowsPaged("en-US")).thenReturn(dummyTVShows);

        Observer<PagedList<TVShow>> observer = mock(Observer.class);

        tvShowListViewModelPaged.getPagedListMoviesLiveData("en-US").observeForever(observer);

        verify(observer).onChanged(pagedList);
    }
}