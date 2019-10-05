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

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MovieViewModelTest {
    private MovieViewModel movieViewModel;
    @Mock
    Observer<MovieDetail> observer;
    @Mock
    LifecycleOwner lifecycleOwner;
    Lifecycle lifecycle;

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        lifecycle = new LifecycleRegistry(lifecycleOwner);
        movieViewModel = new MovieViewModel();
        movieViewModel.getMovieDetail().observeForever(observer);
    }

    @Test
    public void testNull() {
        movieViewModel.fetchMovieDetail();
        ArgumentCaptor<MovieDetail> movieDetailCaptor = ArgumentCaptor.forClass(MovieDetail.class);

        String release_date = "2019-10-02";
        String original_title = "Joker";
        String overview = "During the 1980s, a failed stand-up comedian is driven insane and turns to a life of crime and chaos in Gotham City while becoming an infamous psychopathic crime figure.";
        String homepage = "http://www.jokermovie.net/";
        String poster_path = "/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg";

        verify(observer, times(1)).onChanged(movieDetailCaptor.capture());

        List<MovieDetail> capturedMovieDetail = movieDetailCaptor.getAllValues();
        assertNotNull(capturedMovieDetail.get(0));
        assertEquals(capturedMovieDetail.get(0).getHomepage(), homepage);
        assertEquals(capturedMovieDetail.get(0).getOverview(), overview);
        assertEquals(capturedMovieDetail.get(0).getOriginal_title(), original_title);
        assertEquals(capturedMovieDetail.get(0).getRelease_date(), release_date);
        assertEquals(capturedMovieDetail.get(0).getPoster_path(), poster_path);
    }
}