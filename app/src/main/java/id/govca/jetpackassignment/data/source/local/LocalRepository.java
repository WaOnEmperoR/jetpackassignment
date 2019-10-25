package id.govca.jetpackassignment.data.source.local;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import id.govca.jetpackassignment.data.source.local.entity.Favorite;
import id.govca.jetpackassignment.data.source.local.room.FavoriteDao;
import id.govca.jetpackassignment.data.source.local.room.FavoriteDatabase;
import io.reactivex.Observable;

public class LocalRepository {
    private static LocalRepository INSTANCE;
    private final FavoriteDao mFavoriteDao;
    private ExecutorService executorService;

    private LocalRepository(FavoriteDao mFavoriteDao) {
        this.mFavoriteDao = mFavoriteDao;
        executorService = Executors.newSingleThreadExecutor();
    }

    public static LocalRepository getInstance(FavoriteDao favoriteDao) {
        if (INSTANCE == null) {
            INSTANCE = new LocalRepository(favoriteDao);
        }
        return INSTANCE;
    }

    public Observable<List<Favorite>> getFavorites(int type)
    {
        return Observable.fromCallable(new Callable<List<Favorite>>() {
            @Override
            public List<Favorite> call() {
                return mFavoriteDao.getFavorites(type);
            }
        });
    }

    public Observable<Favorite> getFavoriteDetail(int type, int id)
    {
        return Observable.fromCallable(new Callable<Favorite>() {
            @Override
            public Favorite call() {
                return mFavoriteDao.getFavoriteDetail(type, id);
            }
        });
    }

    public Observable<Long> insertFavorite(Favorite favorite)
    {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() {
                return mFavoriteDao.insertFavorite(favorite);
            }
        });
    }

    public Observable<Integer> deleteFavorite(int type, int id)
    {
        return Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() {
                return mFavoriteDao.deleteFavorite(type, id);
            }
        });
    }

    public Observable<Integer> checkFavorite(int type, int id)
    {
        return Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return mFavoriteDao.checkFavorite(type, id);
            }
        });

    }

    public DataSource.Factory<Integer, Favorite> getAllFavorites(int type)
    {
        return mFavoriteDao.getFavoritesPaging(type);
    }

    public void insert(final Favorite favorite)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                mFavoriteDao.insertFavoritePaging(favorite);
            }
        });
    }

    public void delete(final int type, final int thingsId)
    {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                mFavoriteDao.deleteFavoritePaging(type, thingsId);
            }
        });
    }
}
