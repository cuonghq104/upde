package stp.cuonghq.upde.data.sources.local;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import stp.cuonghq.upde.data.models.dbentities.TripEntity;

@Dao
public interface TripDAO {
    @Query("SELECT * FROM tbl_trip WHERE id_trip = :idTrip")
    List<TripEntity> getAll(String idTrip);

    @Insert
    void insert(TripEntity...tripEntities);

    @Delete
    void delete(TripEntity tripEntity);

}
