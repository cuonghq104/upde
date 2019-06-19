package stp.cuonghq.upde.data.sources;

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

public class TripDatasource {

    public interface RDS {

        void getBookingList(ApiCallback<BookingList> callback);

        void getConfirmList(ApiCallback<BookingList> callback);

        void getCompleteList(ApiCallback<BookingList> callback);

        void confirmBooking(String idHost, String idTrip, ApiCallback<ChangeStatusResponse> callback);

        void completeBooking(String idHost, String idTrip, ApiCallback<ChangeStatusResponse> callback);

        void getAllPrice(String type , int page, ApiCallback<StatisticGetAllPriceResponce> callback);

        void getAllTripCompleteByTimeObservale(String time_begin ,String time_end, long page, ApiCallback<BookingList> callback);

        void getHotelLinks(ApiCallback<ListItemHome> callback);

    }


    public interface LDS {

        void insertNewTrip(TripEntity...entities);

        void removeTrip(TripEntity entity);

        boolean isRead(TripEntity entity);

        TripEntity getTrip(String id);

        //Day
        List<DayStatisticEntity> getAllDay();

        void insertDay(DayStatisticEntity... dayStatisticEntity);

        int getMaxByDay();

        DayStatisticEntity getCurrentDay(String curren);

        void updateDayPageNumber(DayStatisticEntity... dayStatisticEntity);

        DayStatisticEntity getDay();

        void deleteDay();

        //Week
        List<WeekStatisticEntity> getAllWeek();

        void insertWeek(WeekStatisticEntity... weekStatisticEntity);

        int getMaxByWeek();

        WeekStatisticEntity getCurrentWeek(String curren);

        void updateWeekPageNumber(WeekStatisticEntity... weekStatisticEntity);

        WeekStatisticEntity getWeek();

        void deleteWeek();

        //Month
        List<MonthStatisticEntity> getAllMonth();

        void insertMonth(MonthStatisticEntity... monthStatisticEntity);

        int getMaxByMonth();

        MonthStatisticEntity getCurrentMonth(String curren);

        void updateMonthPageNumber(MonthStatisticEntity... monthStatisticEntity);

        MonthStatisticEntity getMonth();

        void deleteMonth();

        //Year
        List<YearStatisticEntity> getAllYear();

        void insertYear(YearStatisticEntity... yearStatisticEntity);

        int getMaxByYear();

        YearStatisticEntity getCurrentYear(String current);

        void updateYearPageNumber(YearStatisticEntity... yearStatisticEntity);

        YearStatisticEntity getYear();

        void deleteYear();
    }
}
