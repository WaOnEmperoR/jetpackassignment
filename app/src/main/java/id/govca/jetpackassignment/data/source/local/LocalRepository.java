package id.govca.jetpackassignment.data.source.local;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Callable;

import id.govca.jetpackassignment.data.source.local.entity.Favorite;
import id.govca.jetpackassignment.data.source.local.room.FavoriteDao;
import id.govca.jetpackassignment.data.source.local.room.FavoriteDatabase;
import io.reactivex.Observable;

public class LocalRepository {
    private static LocalRepository INSTANCE;
    private final FavoriteDao mFavoriteDao;

    private LocalRepository(FavoriteDao mFavoriteDao) {
        this.mFavoriteDao = mFavoriteDao;
    }

    public static LocalRepository getInstance(FavoriteDao favoriteDao) {
        if (INSTANCE == null) {
            INSTANCE = new LocalRepository(favoriteDao);
        }
        return INSTANCE;
    }

    public Observable<List<Favorite>> getFavorites(int type, Context localContext)
    {
        return Observable.fromCallable(new Callable<List<Favorite>>() {
            @Override
            public List<Favorite> call() {
                return FavoriteDatabase.getInstance(localContext)
                        .favoriteDao()
                        .getFavorites(type);
            }
        });
    }

    public Observable<Favorite> getFavoriteDetail(int type, int id, Context localContext)
    {
        return Observable.fromCallable(new Callable<Favorite>() {
            @Override
            public Favorite call() {
                return FavoriteDatabase.getInstance(localContext)
                        .favoriteDao()
                        .getFavoriteDetail(type, id);
            }
        });
    }

    public Observable<Long> insertFavorite(Favorite favorite, Context localContext)
    {
        return Observable.fromCallable(new Callable<Long>() {
            @Override
            public Long call() {
                return FavoriteDatabase.getInstance(localContext)
                        .favoriteDao()
                        .insertFavorite(favorite);
            }
        });
    }

    public Observable<Void> deleteFavorite(int type, int id, Context localContext)
    {
        return Observable.fromCallable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                return FavoriteDatabase.getInstance(localContext)
                        .favoriteDao()
                        .deleteFavorite(type, id);
            }
        });
    }

    public Observable<Integer> checkFavorite(int type, int id, Context localContext)
    {
        return Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return FavoriteDatabase.getInstance(localContext)
                        .favoriteDao()
                        .checkFavorite(type, id);
            }
        });

    }
}
