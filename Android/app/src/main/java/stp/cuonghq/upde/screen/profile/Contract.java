package stp.cuonghq.upde.screen.profile;

import stp.cuonghq.upde.data.models.LoginData;

public class Contract {

    interface View {
        void logOutSuccess(String msg);
    }

    interface Presenter {
        void logOut();
        LoginData getUserData();
    }
}
