package stp.cuonghq.upde.data.sources.local;

import android.util.Log;

import java.util.List;

import stp.cuonghq.upde.data.models.dbentities.DayStatisticEntity;
import stp.cuonghq.upde.data.models.dbentities.MonthStatisticEntity;
import stp.cuonghq.upde.data.models.dbentities.WeekStatisticEntity;
import stp.cuonghq.upde.data.models.dbentities.TripEntity;
import stp.cuonghq.upde.data.models.dbentities.YearStatisticEntity;
import stp.cuonghq.upde.data.sources.TripDatasource;

public class TripLDS implements TripDatasource.LDS {

    public static TripLDS sInstance;

    public static TripLDS getInstance() {
        if (sInstance == null) {
            sInstance = new TripLDS();
        }
        return sInstance;
    }

    @Override
    public void insertNewTrip(TripEntity... entities) {
        AppDatabase database = AppDatabase.getInstance();
        database.tripDAO().insert(entities);
    }

    @Override
    public void removeTrip(TripEntity entity) {
        AppDatabase database = AppDatabase.getInstance();
        database.tripDAO().delete(entity);
    }

    @Override
    public boolean isRead(TripEntity entity) {
        AppDatabase database = AppDatabase.getInstance();
        return (database.tripDAO().getAll(entity.getIdTrip()).size() == 0);
    }

    @Override
    public TripEntity getTrip(String id) {
        AppDatabase database = AppDatabase.getInstance();
        List<TripEntity> entities = database.tripDAO().getAll(id);
        return (entities.size() == 0) ? null : entities.get(0);
    }

    //Day

    @Override
    public List<DayStatisticEntity> getAllDay() {
        AppDatabase database = AppDatabase.getInstance();
        List<DayStatisticEntity> entities = database.dayStatisticDAO().getAllDay();
        return (entities.size() == 0) ? null : entities;
    }

    @Override
    public void insertDay(DayStatisticEntity... dayStatisticEntity) {
        AppDatabase database = AppDatabase.getInstance();
        database.dayStatisticDAO().insertDay(dayStatisticEntity);
    }

    @Override
    public int getMaxByDay() {
        AppDatabase database = AppDatabase.getInstance();
        List<DayStatisticEntity> entities = database.dayStatisticDAO().getAllDay();
        if (entities.size() == 0){
            Log.d("hehe","nodata return 0");
            return 0;
        } else {
            Log.d("hehe","return "+database.dayStatisticDAO().getMaxByDay());

            return database.dayStatisticDAO().getMaxByDay();
        }
    }

    @Override
    public DayStatisticEntity getCurrentDay(String curren) {
        AppDatabase database = AppDatabase.getInstance();
        List<DayStatisticEntity> entities = database.dayStatisticDAO().getAllDay();
        if (entities.size() == 0){
            Log.e("hehe","get all day null");
            return null;
        } else {
            Log.e("hehe","not null return current day "+database.dayStatisticDAO().getCurrentDay());
            return database.dayStatisticDAO().getCurrentDay();
        }
    }

    @Override
    public void updateDayPageNumber(DayStatisticEntity... dayStatisticEntity) {
        AppDatabase database = AppDatabase.getInstance();
        database.dayStatisticDAO().updateDayPageNumber(dayStatisticEntity);
    }

    @Override
    public DayStatisticEntity getDay() {
        return null;
    }

    @Override
    public void deleteDay(){
        AppDatabase database = AppDatabase.getInstance();
        database.dayStatisticDAO().deleteDay();
    }

    //Week
    @Override
    public List<WeekStatisticEntity> getAllWeek() {
        AppDatabase database = AppDatabase.getInstance();
        List<WeekStatisticEntity> entities = database.weekStatisticDAO().getAllWeek();
        return (entities.size() == 0) ? null : entities;
    }

    @Override
    public void insertWeek(WeekStatisticEntity... weekStatisticEntity) {
        AppDatabase database = AppDatabase.getInstance();
        database.weekStatisticDAO().insertWeek(weekStatisticEntity);
    }

    @Override
    public int getMaxByWeek() {
        AppDatabase database = AppDatabase.getInstance();
        List<WeekStatisticEntity> entities = database.weekStatisticDAO().getAllWeek();
        if (entities.size() == 0){
            return 0;
        } else {
            return database.weekStatisticDAO().getMaxByWeek();
        }
    }

    @Override
    public WeekStatisticEntity getCurrentWeek(String curren) {
        AppDatabase database = AppDatabase.getInstance();
        List<WeekStatisticEntity> entities = database.weekStatisticDAO().getAllWeek();
        if (entities.size() == 0){
            return null;
        } else {
            return database.weekStatisticDAO().getCurrentWeek();
        }
    }

    @Override
    public void updateWeekPageNumber(WeekStatisticEntity... weekStatisticEntity) {
        AppDatabase database = AppDatabase.getInstance();
        database.weekStatisticDAO().updateWeekPageNumber(weekStatisticEntity);
    }

    @Override
    public WeekStatisticEntity getWeek() {
        return null;
    }

    @Override
    public void deleteWeek(){
        AppDatabase database = AppDatabase.getInstance();
        database.weekStatisticDAO().deleteWeek();
    }

    //Month
    @Override
    public List<MonthStatisticEntity> getAllMonth() {
        AppDatabase database = AppDatabase.getInstance();
        List<MonthStatisticEntity> entities = database.monthStatisticDAO().getAllMonth();
        return (entities.size() == 0) ? null : entities;
    }

    @Override
    public void insertMonth(MonthStatisticEntity... monthStatisticEntity) {
        AppDatabase database = AppDatabase.getInstance();
        database.monthStatisticDAO().insertMonth(monthStatisticEntity);
    }

    @Override
    public int getMaxByMonth() {
        AppDatabase database = AppDatabase.getInstance();
        List<MonthStatisticEntity> entities = database.monthStatisticDAO().getAllMonth();
        if (entities.size() == 0){
            return 0;
        } else {
            return database.monthStatisticDAO().getMaxByMonth();
        }
    }

    @Override
    public MonthStatisticEntity getCurrentMonth(String curren) {
        AppDatabase database = AppDatabase.getInstance();
        List<MonthStatisticEntity> entities = database.monthStatisticDAO().getAllMonth();
        if (entities.size() == 0){
            return null;
        } else {
            return database.monthStatisticDAO().getCurrentMonth();
        }
    }

    @Override
    public void updateMonthPageNumber(MonthStatisticEntity... monthStatisticEntity) {
        AppDatabase database = AppDatabase.getInstance();
        database.monthStatisticDAO().updateMonthPageNumber(monthStatisticEntity);
    }

    @Override
    public MonthStatisticEntity getMonth() {
        return null;
    }

    @Override
    public void deleteMonth(){
        AppDatabase database = AppDatabase.getInstance();
        database.monthStatisticDAO().deleteMonth();
    }

    //Year
    @Override
    public List<YearStatisticEntity> getAllYear() {
        AppDatabase database = AppDatabase.getInstance();
        List<YearStatisticEntity> entities = database.yearStatisticDAO().getAllYear();
        return (entities.size() == 0) ? null : entities;
    }

    @Override
    public void insertYear(YearStatisticEntity... yearStatisticEntity) {
        AppDatabase database = AppDatabase.getInstance();
        database.yearStatisticDAO().insertYear(yearStatisticEntity);
    }

    @Override
    public int getMaxByYear() {
        AppDatabase database = AppDatabase.getInstance();
        List<YearStatisticEntity> entities = database.yearStatisticDAO().getAllYear();
        if (entities.size() == 0){
            return 0;
        } else {
            return database.yearStatisticDAO().getMaxByYear();
        }
    }

    @Override
    public YearStatisticEntity getCurrentYear(String curren) {
        AppDatabase database = AppDatabase.getInstance();
        List<YearStatisticEntity> entities = database.yearStatisticDAO().getAllYear();
        if (entities.size() == 0){
            return null;
        } else {
            return database.yearStatisticDAO().getCurrentYear();
        }
    }

    @Override
    public void updateYearPageNumber(YearStatisticEntity... yearStatisticEntity) {
        AppDatabase database = AppDatabase.getInstance();
        database.yearStatisticDAO().updateYearPageNumber(yearStatisticEntity);
    }

    @Override
    public YearStatisticEntity getYear() {
        return null;
    }

    @Override
    public void deleteYear(){
        AppDatabase database = AppDatabase.getInstance();
        database.yearStatisticDAO().deleteYear();
    }
}
