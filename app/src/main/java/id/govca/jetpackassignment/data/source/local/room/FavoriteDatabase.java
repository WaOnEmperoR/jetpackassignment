package id.govca.jetpackassignment.data.source.local.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import id.govca.jetpackassignment.data.source.local.entity.Favorite;

@Database(entities = {Favorite.class},
        version = 1,
        exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {
    private static final Object sLock = new Object();
    private static FavoriteDatabase INSTANCE;

    public static FavoriteDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        FavoriteDatabase.class, "Favorites.db")
                        .build();
            }
            return INSTANCE;
        }
    }

    public abstract FavoriteDao favoriteDao();
}
