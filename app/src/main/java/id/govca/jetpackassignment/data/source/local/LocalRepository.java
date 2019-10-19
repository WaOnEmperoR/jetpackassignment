package id.govca.jetpackassignment.data.source.local;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.govca.jetpackassignment.data.source.local.entity.Favorite;
import id.govca.jetpackassignment.data.source.local.room.FavoriteDao;

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

    public LiveData<List<Favorite>> getFavorites(int type)
    {
        return mFavoriteDao.getFavorites(type);
    }

    public LiveData<Favorite> getFavoriteDetail(int type, int id)
    {
        return mFavoriteDao.getFavoriteDetail(type, id);
    }

    public long insertFavorite(Favorite favorite)
    {
        return mFavoriteDao.insertFavorite(favorite);
    }

    public void deleteFavorite(int type, int id)
    {
        mFavoriteDao.deleteFavorite(type, id);
    }

    public int checkFavorite(int type, int id)
    {
        return mFavoriteDao.checkFavorite(type, id);
    }
}
