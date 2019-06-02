package stp.cuonghq.upde.screen.home.complete;

import stp.cuonghq.upde.data.models.BookingList;

public class Contract {

    interface Presenter {

        void getCompleteList();
    }

    interface View {

        void onLoading();
        void getCompleteListSuccess(BookingList list);
        void getCompleteListFailed(String msg);
    }
}
