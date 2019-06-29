package stp.cuonghq.upde.screen.profile;

import android.support.v7.app.AppCompatActivity;

import stp.cuonghq.upde.commons.AvatarResponse;
import stp.cuonghq.upde.data.models.LoginData;

public class Contract {

    interface View {
        void logOutSuccess(String msg);
        void changeProfileImageSuccess(AvatarResponse response);
        void changeProfileImageFailed(String msg);
    }

    interface Presenter {
        void logOut();
        LoginData getUserData();
        String getLoginType();

        void changeProfileImage(String fileType, String filePath);
    }
}
