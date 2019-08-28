package stp.cuonghq.upde.data.sources.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import stp.cuonghq.upde.data.models.dbentities.WeekStatisticEntity;

/**
 * Created by cuong.hq1 on 6/17/2019.
 */

@Dao
public interface WeekStatisticDAO {
    @Query("SELECT * FROM tbl_week_statistic")
    List<WeekStatisticEntity> getAllWeek();

    @Insert
    void insertWeek(WeekStatisticEntity... weekStatisticEntity);

    @Query("SELECT COUNT(*) FROM tbl_week_statistic GROUP BY id")
    int getTotalOfDateTime();

    @Query("SELECT MAX(page_number) FROM tbl_week_statistic GROUP BY id")
    int getMaxByWeek();

    @Query("SELECT * FROM tbl_week_statistic WHERE id = 1")
    WeekStatisticEntity getCurrentWeek();

    @Update
    void updateWeekPageNumber(WeekStatisticEntity... weekStatisticEntity);

    @Query("SELECT * FROM tbl_week_statistic WHERE id = :mId")
    WeekStatisticEntity getSomeWeek(String mId);

    @Query("DELETE FROM tbl_week_statistic")
    void deleteWeek();
}
