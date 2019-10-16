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
import id.govca.jetpackassignment.pojo.TVShow;
import id.govca.jetpackassignment.pojo.TVShowDetail;
import id.govca.jetpackassignment.pojo.TVShowList;
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
    TVShowList tvShowList = new TVShowList();

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
    public void getAllTVShows()
    {
        tvShowList.setTvShowArrayList(DummyData.generateDummyTVShows());
        when(remote.getTVShowList()).thenReturn(tvShowList);
        List<TVShow> myTVShowList = movieRepository.getAllTVShows();
        verify(remote).getTVShowList();
        assertNotNull(myTVShowList);
        assertEquals(myTVShowList.size(), tvShowList.getTvShowArrayList().size());
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

    @Test
    public void getTVShowDetail() {
        TVShow tvShow = DummyData.generateDummyTVShows().get(0);

        TVShowDetail tvShowDetail = new TVShowDetail();
        tvShowDetail.setId(tvShow.getId());
        tvShowDetail.setFirst_air_date(tvShow.getFirst_air_date());
        tvShowDetail.setName(tvShow.getName());
        tvShowDetail.setOverview(tvShow.getOverview());
        tvShowDetail.setVote_average(tvShow.getVote_average());

        when(remote.getTVShowDetail(tvShow.getId())).thenReturn(tvShowDetail);
        TVShowDetail tsd = movieRepository.getTVShowDetail(tvShow.getId());
        verify(remote).getTVShowDetail(tvShow.getId());
        assertNotNull(tsd);
        assertEquals(tsd.getName(), tvShow.getName());
        assertEquals(tsd.getOverview(), tvShow.getOverview());
        assertEquals(tsd.getFirst_air_date(), tvShow.getFirst_air_date());
    }


}