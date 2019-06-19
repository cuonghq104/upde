package stp.cuonghq.upde.data.repositories;

import java.util.ArrayList;
import java.util.List;

import stp.cuonghq.upde.commons.ApiCallback;
import stp.cuonghq.upde.data.models.BookingList;
import stp.cuonghq.upde.data.models.ChangeStatusResponse;
import stp.cuonghq.upde.data.models.ListItemHome;
import stp.cuonghq.upde.data.models.StatisticGetAllPriceResponce;
import stp.cuonghq.upde.data.models.dbentities.DayStatisticEntity;
import stp.cuonghq.upde.data.models.dbentities.MonthStatisticEntity;
import stp.cuonghq.upde.data.models.dbentities.WeekStatisticEntity;
import stp.cuonghq.upde.data.models.dbentities.TripEntity;
import stp.cuonghq.upde.data.models.dbentities.YearStatisticEntity;
import stp.cuonghq.upde.data.sources.local.AppDatabase;
import stp.cuonghq.upde.data.sources.local.TripLDS;
import stp.cuonghq.upde.data.sources.remote.TripRDS;

public class TripRepository {

    private TripRDS rds;
    private TripLDS lds;

    private volatile static TripRepository sInstance;

    private TripRepository(TripRDS rds, TripLDS lds) {
        this.rds = rds;
        this.lds = lds;
    }

    public static TripRepository getInstance() {
        if (sInstance == null) {
            sInstance = new TripRepository(TripRDS.getInstance(), TripLDS.getInstance());
        }
        return sInstance;
    }

    public void getBookingList(ApiCallback<BookingList> callback) {
        if (rds != null) {
            rds.getBookingList(callback);
        }
    }

    public void getConfirmList(ApiCallback<BookingList> callback) {
        if (rds != null) {
            rds.getConfirmList(callback);
        }
    }

    public void getCompleteList(ApiCallback<BookingList> callback) {
        if (rds != null) {
            rds.getCompleteList(callback);
        }
    }

    public void confirmBooking(String idHost, String idTrip, ApiCallback<ChangeStatusResponse> callback) {
        if (rds != null) {
            rds.confirmBooking(idHost, idTrip, callback);
        }
    }

    public void completeBooking(String idHost, String idTrip, ApiCallback<ChangeStatusResponse> callback) {
        if (rds != null) {
            rds.completeBooking(idHost, idTrip, callback);
        }
    }

    public void getAllPrice(String type, int page, ApiCallback<StatisticGetAllPriceResponce> callback) {
        if (rds != null) {
            rds.getAllPrice(type, page, callback);
        }
    }

    public void getAllTripCompleteByTimeObservale(String time_begin,String time_end, long page, ApiCallback<BookingList> callback) {
        if (rds != null) {
            rds.getAllTripCompleteByTimeObservale(time_begin, time_end,page, callback);
        }
    }

    public void getHotelLinks(ApiCallback<ListItemHome> callback) {
        if (rds != null) {
            rds.getHotelLinks(callback);
        }
    }

    public boolean checkIsRead(String idTrip) {
        if (lds != null) {
            TripEntity entity = new TripEntity();
            entity.setIdTrip(idTrip);
            return lds.isRead(entity);
        }
        return false;
    }

    public void addToUnreadList(String idTrip) {
        if (lds != null) {
            TripEntity entity = new TripEntity();
            entity.setIdTrip(idTrip);
            lds.insertNewTrip(entity);
        }
    }

    public void removeFromUnreadList(String idTrip) {
        if (lds != null) {
            TripEntity entity = lds.getTrip(idTrip);
            lds.removeTrip(entity);
        }
    }

    //Day
    public List<DayStatisticEntity> getAllDay() {
        if (lds != null) {
            return  lds.getAllDay().size() == 0 ? null : lds.getAllDay();
        }
        return null;
    }


    public void insertDay(String time, String price , int pagenumber , String numbertrip) {
        if (lds != null) {
            DayStatisticEntity entity = new DayStatisticEntity();
            entity.setDay(time);
            entity.setPrice(price);
            entity.setNumbertrip(numbertrip);
            entity.setPageNumber(pagenumber);
            lds.insertDay(entity);
        }
    }


    public int getMaxByDay() {
        if (lds != null) {
            return  lds.getMaxByDay();
        }
        return 0;
    }


    public DayStatisticEntity getCurrentDay(String curren) {
        if (lds != null) {
            return  lds.getCurrentDay(curren);
        }
        return null;
    }


    public void updateDayPageNumber(String time, String price , int pagenumber , String numbertrip) {
        if (lds != null) {
            DayStatisticEntity entity = new DayStatisticEntity();
            entity.setDay(time);
            entity.setPrice(price);
            entity.setNumbertrip(numbertrip);
            entity.setPageNumber(pagenumber);
            lds.updateDayPageNumber(entity);
        }
    }

    public void deleteDay() {
        if (lds != null) {
            lds.deleteDay();
        }
    }

    //Week
    public List<WeekStatisticEntity> getAllWeek() {
        if (lds != null) {
            return  lds.getAllWeek().size() == 0 ? null : lds.getAllWeek();
        }
        return null;
    }


    public void insertWeek(String time, String price , int pagenumber , String numbertrip) {
        if (lds != null) {
            WeekStatisticEntity entity = new WeekStatisticEntity();
            entity.setTime(time);
            entity.setPrice(price);
            entity.setNumbertrip(numbertrip);
            entity.setPageNumber(pagenumber);
            lds.insertWeek(entity);
        }
    }


    public int getMaxByWeek() {
        if (lds != null) {
            return  lds.getMaxByWeek();
        }
        return 0;
    }


    public WeekStatisticEntity getCurrentWeek(String curren) {
        if (lds != null) {
            return  lds.getCurrentWeek(curren);
        }
        return null;
    }


    public void updateWeekPageNumber(String time, String price , int pagenumber , String numbertrip) {
        if (lds != null) {
            WeekStatisticEntity entity = new WeekStatisticEntity();
            entity.setTime(time);
            entity.setPrice(price);
            entity.setNumbertrip(numbertrip);
            entity.setPageNumber(pagenumber);
            lds.updateWeekPageNumber(entity);
        }
    }

    public void deleteWeek() {
        if (lds != null) {
            lds.deleteWeek();
        }
    }

    //Month
    public List<MonthStatisticEntity> getAllMonth() {
        if (lds != null) {
            return  lds.getAllMonth().size() == 0 ? null : lds.getAllMonth();
        }
        return null;
    }


    public void insertMonth(String time, String price , int pagenumber , String numbertrip) {
        if (lds != null) {
            MonthStatisticEntity entity = new MonthStatisticEntity();
            entity.setTime(time);
            entity.setPrice(price);
            entity.setNumbertrip(numbertrip);
            entity.setPageNumber(pagenumber);
            lds.insertMonth(entity);
        }
    }


    public int getMaxByMonth() {
        if (lds != null) {
            return  lds.getMaxByMonth();
        }
        return 0;
    }


    public MonthStatisticEntity getCurrentMonth(String curren) {
        if (lds != null) {
            return  lds.getCurrentMonth(curren);
        }
        return null;
    }


    public void updateMonthPageNumber(String time, String price , int pagenumber , String numbertrip) {
        if (lds != null) {
            MonthStatisticEntity entity = new MonthStatisticEntity();
            entity.setTime(time);
            entity.setPrice(price);
            entity.setNumbertrip(numbertrip);
            entity.setPageNumber(pagenumber);
            lds.updateMonthPageNumber(entity);
        }
    }

    public void deleteMonth() {
        if (lds != null) {
            lds.deleteMonth();
        }
    }

    //Year
    public List<YearStatisticEntity> getAllYear() {
        if (lds != null) {
            return  lds.getAllYear().size() == 0 ? null : lds.getAllYear();
        }
        return null;
    }


    public void insertYear(String time, String price , int pagenumber , String numbertrip) {
        if (lds != null) {
            YearStatisticEntity entity = new YearStatisticEntity();
            entity.setTime(time);
            entity.setPrice(price);
            entity.setNumbertrip(numbertrip);
            entity.setPageNumber(pagenumber);
            lds.insertYear(entity);
        }
    }


    public int getMaxByYear() {
        if (lds != null) {
            return  lds.getMaxByYear();
        }
        return 0;
    }


    public YearStatisticEntity getCurrentYear(String curren) {
        if (lds != null) {
            return  lds.getCurrentYear(curren);
        }
        return null;
    }


    public void updateYearPageNumber(String time, String price , int pagenumber , String numbertrip) {
        if (lds != null) {
            YearStatisticEntity entity = new YearStatisticEntity();
            entity.setTime(time);
            entity.setPrice(price);
            entity.setNumbertrip(numbertrip);
            entity.setPageNumber(pagenumber);
            lds.updateYearPageNumber(entity);
        }
    }

    public void deleteYear() {
        if (lds != null) {
            lds.deleteYear();
        }
    }
}
