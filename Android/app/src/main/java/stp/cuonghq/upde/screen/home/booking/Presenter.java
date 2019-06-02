package stp.cuonghq.upde.screen.home.booking;

import org.w3c.dom.Entity;

import stp.cuonghq.upde.commons.ApiCallback;
import stp.cuonghq.upde.data.models.BookingList;
import stp.cuonghq.upde.data.models.BookingResp;
import stp.cuonghq.upde.data.models.dbentities.TripEntity;
import stp.cuonghq.upde.data.repositories.TripRepository;

public class Presenter implements Contract.Presenter {

    private Contract.View mView;
    private TripRepository mRepository;

    public Presenter() {
        mRepository = TripRepository.getInstance();
    }


    public void setView(Contract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getBookingList() {
        mView.doGetBookingList();
        mRepository.getBookingList(new ApiCallback<BookingList>() {
            @Override
            public void success(BookingList data, String msg) {
                mView.getBookingListSuccess(data);

                for (BookingResp resp : data.getList()) {
                    resp.setRead(mRepository.checkIsRead(resp.getIdTrip()));
                }
            }

            @Override
            public void failed(String msg) {
                mView.getBookingListFailed(msg);
            }
        });
    }

    @Override
    public void addToUnreadTripList(String id) {
        mRepository.addToUnreadList(id);
    }
}
