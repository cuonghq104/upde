package stp.cuonghq.upde.screen.home.confirm;

import stp.cuonghq.upde.data.models.BookingList;

public class Contract {

    interface Presenter {

        void loadConfirmList();
    }

    interface View {

        void doLoading();
        void getConfirmListSuccess(BookingList bookingList);
        void getConfirmListFailed(String msg);
    }
}
