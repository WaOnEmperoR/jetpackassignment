package id.govca.jetpackassignment.data.source;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Rule;
import org.junit.Test;

import id.govca.jetpackassignment.data.source.remote.RemoteRepository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class MovieRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private RemoteRepository remote = mock(RemoteRepository.class);

    @Test
    public void getAllMovies() {
    }
}