package id.govca.jetpackassignment.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import id.govca.jetpackassignment.pojo.Movie;
import id.govca.jetpackassignment.pojo.MovieDetail;
import id.govca.jetpackassignment.pojo.TVShowDetail;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TvShowViewModelTest {
    private TvShowViewModel tvShowViewModel;
    @Mock
    Observer<TVShowDetail> observer;
    @Mock
    LifecycleOwner lifecycleOwner;
    Lifecycle lifecycle;

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        lifecycle = new LifecycleRegistry(lifecycleOwner);
        tvShowViewModel = new TvShowViewModel();
        tvShowViewModel.getTvShowDetail().observeForever(observer);
    }

    @Test
    public void testNull() {
        tvShowViewModel.fetchTvShowDetail();
        ArgumentCaptor<TVShowDetail> tvShowDetailCaptor = ArgumentCaptor.forClass(TVShowDetail.class);

        String name = "The Simpsons";
        String homepage = "http://www.thesimpsons.com/";
        String overview = "Set in Springfield, the average American town, the show focuses on the antics and everyday adventures of the Simpson family; Homer, Marge, Bart, Lisa and Maggie, as well as a virtual cast of thousands. Since the beginning, the series has been a pop culture icon, attracting hundreds of celebrities to guest star. The show has also made name for itself in its fearless satirical take on politics, media and American life in general.";
        String poster_path = "/yTZQkSsxUFJZJe67IenRM0AEklc.jpg";
        String first_air_date = "1989-12-17";

        verify(observer, times(1)).onChanged(tvShowDetailCaptor.capture());

        List<TVShowDetail> capturedTvShowDetail = tvShowDetailCaptor.getAllValues();
        assertNotNull(capturedTvShowDetail.get(0));
        assertEquals(capturedTvShowDetail.get(0).getFirst_air_date(), first_air_date);
        assertEquals(capturedTvShowDetail.get(0).getPoster_path(), poster_path);
        assertEquals(capturedTvShowDetail.get(0).getOverview(), overview);
        assertEquals(capturedTvShowDetail.get(0).getHomepage(), homepage);
        assertEquals(capturedTvShowDetail.get(0).getName(), name);
    }

}