package stp.cuonghq.upde.data.sources.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import stp.cuonghq.upde.data.models.dbentities.MonthStatisticEntity;

/**
 * Created by cuong.hq1 on 6/17/2019.
 */

@Dao
public interface MonthStatisticDAO {
    @Query("SELECT * FROM tbl_month_statistic")
    List<MonthStatisticEntity> getAllMonth();

    @Insert
    void insertMonth(MonthStatisticEntity... MonthStatisticEntity);

    @Query("SELECT COUNT(*) FROM tbl_month_statistic GROUP BY id")
    int getTotalOfDateTime();

    @Query("SELECT MAX(page_number) FROM tbl_month_statistic GROUP BY id")
    int getMaxByMonth();

    @Query("SELECT * FROM tbl_month_statistic WHERE id = 1")
    MonthStatisticEntity getCurrentMonth();

    @Update
    void updateMonthPageNumber(MonthStatisticEntity... MonthStatisticEntity);

    @Query("SELECT * FROM tbl_month_statistic WHERE id = :mId")
    MonthStatisticEntity getSomeMonth(String mId);

    @Query("DELETE FROM tbl_month_statistic")
    void deleteMonth();
}
