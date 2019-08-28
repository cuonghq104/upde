package stp.cuonghq.upde.data.sources.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import stp.cuonghq.upde.data.models.dbentities.DayStatisticEntity;

/**
 * Created by cuong.hq1 on 6/17/2019.
 */

@Dao
public interface DayStatisticDAO {
    @Query("SELECT * FROM tbl_day_statistic")
    List<DayStatisticEntity> getAllDay();

    @Insert
    void insertDay(DayStatisticEntity... dayStatisticEntity);

    @Query("SELECT COUNT(*) FROM tbl_day_statistic GROUP BY id")
    int getTotalOfDateTime();

    @Query("SELECT MAX(page_number) FROM tbl_day_statistic")
    int getMaxByDay();

    @Query("SELECT * FROM tbl_day_statistic WHERE id = 1")
    DayStatisticEntity getCurrentDay();

    @Update
    void updateDayPageNumber(DayStatisticEntity... dayStatisticEntity);

    @Query("SELECT * FROM tbl_day_statistic WHERE id = :mId")
    DayStatisticEntity getSomeDay(String mId);

    @Query("DELETE FROM tbl_day_statistic")
    void deleteDay();
}
