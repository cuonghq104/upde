package stp.cuonghq.upde.screen.home.booking;

import stp.cuonghq.upde.data.models.BookingList;

public class Contract {

    interface View {
        void doGetBookingList();
        void getBookingListSuccess(BookingList list);
        void getBookingListFailed(String msg);
    }

    interface Presenter {
        void getBookingList();
        void addToUnreadTripList(String id);
    }
}
