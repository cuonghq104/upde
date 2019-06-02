package stp.cuonghq.upde.data.repositories;

import java.util.List;

import stp.cuonghq.upde.commons.ApiCallback;
import stp.cuonghq.upde.data.models.BookingList;
import stp.cuonghq.upde.data.models.BookingResp;
import stp.cuonghq.upde.data.models.ChangeStatusResponse;
import stp.cuonghq.upde.data.models.dbentities.TripEntity;
import stp.cuonghq.upde.data.sources.local.TripLDS;
import stp.cuonghq.upde.data.sources.remote.TripRDS;

public class TripRepository {

    private TripRDS rds;
    private TripLDS lds;

    private static TripRepository sInstance;

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
}
