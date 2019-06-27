package stp.cuonghq.upde.screen.editinfo;

import stp.cuonghq.upde.data.models.LoginData;

public class Contract {

    interface View {
        void doneUpdateInformation(boolean success, String msg);
    }

    interface Presenter {
        LoginData getLoginData();
        void updateInformation(String phoneNumber, String name);
    }
}
