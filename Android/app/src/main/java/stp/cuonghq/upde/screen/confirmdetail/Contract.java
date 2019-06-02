package stp.cuonghq.upde.screen.confirmdetail;

import stp.cuonghq.upde.data.models.ChangeStatusData;

public class Contract {

    interface View {

        void doLoading();
        void completeSuccess(ChangeStatusData data, String msg);
        void completeFailed(String msg);
    }

    interface Presenter {

        void completeBooking(String idTrip);
    }
}
