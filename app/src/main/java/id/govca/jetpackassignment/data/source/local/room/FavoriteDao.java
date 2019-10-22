package id.govca.jetpackassignment.data.source.local.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import id.govca.jetpackassignment.data.source.local.entity.Favorite;

@Dao
public interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertFavorite(Favorite favorite);

    @Transaction
    @Query("SELECT * FROM favorite where type = :type")
    List<Favorite> getFavorites(int type);

    @Query("SELECT * FROM favorite where type = :type and thingsId = :id")
    Favorite getFavoriteDetail(int type, int id);

    @Query("SELECT count(*) FROM favorite WHERE type = :type AND thingsId = :thingsId")
    Integer checkFavorite(int type, int thingsId);

    @Query("DELETE FROM favorite WHERE type = :type AND thingsId = :thingsId")
    int deleteFavorite(int type, int thingsId);

}
