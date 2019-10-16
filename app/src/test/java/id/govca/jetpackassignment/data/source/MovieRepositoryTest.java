package id.govca.jetpackassignment.data.source;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import id.govca.jetpackassignment.data.source.remote.RemoteRepository;
import id.govca.jetpackassignment.pojo.Movie;
import id.govca.jetpackassignment.pojo.MovieDetail;
import id.govca.jetpackassignment.pojo.MovieList;
import id.govca.jetpackassignment.utils.DummyData;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private FakeRemoteRepository remote = mock(FakeRemoteRepository.class);
    private FakeMovieRepository movieRepository = new FakeMovieRepository(remote);

    MovieList movieList = new MovieList();
    MovieDetail movieDetail = new MovieDetail();

    @Test
    public void getAllMovies() {
        movieList.setMovieArrayList(DummyData.generateDummyMovies());
        when(remote.getMovieList()).thenReturn(movieList);
        List<Movie> myMovieList = movieRepository.getAllMovies();
        verify(remote).getMovieList();
        assertNotNull(myMovieList);
        assertEquals(myMovieList.size(), movieList.getMovieArrayList().size());
    }

    @Test
    public void getMovieDetail() {
        Movie movie = DummyData.generateDummyMovies().get(0);

        MovieDetail movieDetail = new MovieDetail();
        movieDetail.setId(movie.getId());
        movieDetail.setOverview(movie.getOverview());
        movieDetail.setOriginal_title(movie.getTitle());
        movieDetail.setRelease_date(movie.getRelease_date());

        when(remote.getMovieDetail(movie.getId())).thenReturn(movieDetail);
        MovieDetail md = movieRepository.getMovieDetail(movie.getId());
        verify(remote).getMovieDetail(movie.getId());
        assertNotNull(md);
        assertEquals(md.getOriginal_title(), movie.getTitle());
        assertEquals(md.getRelease_date(), movie.getRelease_date());
        assertEquals(md.getOverview(), movie.getOverview());
    }

}