package stp.cuonghq.upde.data.sources.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

import stp.cuonghq.upde.data.models.dbentities.WeekStatisticEntity;
import stp.cuonghq.upde.data.models.dbentities.YearStatisticEntity;

/**
 * Created by cuong.hq1 on 6/17/2019.
 */

@Dao
public interface YearStatisticDAO {
    @Query("SELECT * FROM tbl_year_statistic")
    List<YearStatisticEntity> getAllYear();

    @Insert
    void insertYear(YearStatisticEntity... YearStatisticEntity);

    @Query("SELECT COUNT(*) FROM tbl_year_statistic GROUP BY id")
    int getTotalOfDateTime();

    @Query("SELECT MAX(page_number) FROM tbl_year_statistic GROUP BY id")
    int getMaxByYear();

    @Query("SELECT * FROM tbl_year_statistic WHERE id = 1")
    YearStatisticEntity getCurrentYear();

    @Update
    void updateYearPageNumber(YearStatisticEntity... YearStatisticEntity);

    @Query("SELECT * FROM tbl_year_statistic WHERE id = :mId")
    YearStatisticEntity getSomeYear(String mId);

    @Query("DELETE FROM tbl_year_statistic")
    void deleteYear();
}
