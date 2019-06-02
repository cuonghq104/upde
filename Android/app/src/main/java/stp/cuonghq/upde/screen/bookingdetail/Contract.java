package stp.cuonghq.upde.screen.bookingdetail;

import stp.cuonghq.upde.data.models.BookingResp;
import stp.cuonghq.upde.data.models.ChangeStatusData;

public class Contract {

    interface View {
        void doLoading();
        void confirmSuccess(ChangeStatusData data, String msg);
        void confirmFailed(String msg);
    }

    interface Presenter {
        void confirm(String idTrip);
        void removeFromUnreadList(String idTrip);
    }
}
