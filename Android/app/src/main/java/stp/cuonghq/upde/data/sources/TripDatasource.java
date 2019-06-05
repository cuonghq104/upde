package stp.cuonghq.upde.data.sources;

import stp.cuonghq.upde.commons.ApiCallback;
import stp.cuonghq.upde.data.models.BookingList;
import stp.cuonghq.upde.data.models.BookingResp;
import stp.cuonghq.upde.data.models.ChangeStatusResponse;
import stp.cuonghq.upde.data.models.StatisticGetAllPriceResponce;
import stp.cuonghq.upde.data.models.dbentities.TripEntity;

public class TripDatasource {

    public interface RDS {

        void getBookingList(ApiCallback<BookingList> callback);

        void getConfirmList(ApiCallback<BookingList> callback);

        void getCompleteList(ApiCallback<BookingList> callback);

        void confirmBooking(String idHost, String idTrip, ApiCallback<ChangeStatusResponse> callback);

        void completeBooking(String idHost, String idTrip, ApiCallback<ChangeStatusResponse> callback);

        void getAllPrice(String type , int page, ApiCallback<StatisticGetAllPriceResponce> callback);

        void getAllTripCompleteByTimeObservale(String time_begin ,String time_end, long page, ApiCallback<BookingList> callback);

    }


    public interface LDS {

        void insertNewTrip(TripEntity...entities);

        void removeTrip(TripEntity entity);

        boolean isRead(TripEntity entity);

        TripEntity getTrip(String id);
    }
}
