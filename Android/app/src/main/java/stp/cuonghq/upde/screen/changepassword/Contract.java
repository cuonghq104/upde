package stp.cuonghq.upde.screen.changepassword;

public class Contract {

    interface View {
        void passwordDoNotMatch(String msg);
        void changePasswordSuccess(String msg);
        void changePasswordFailed(String msg);
    }

    interface Presenter {
        void changePassword(String oldPassword, String newPassword, String confirmPassword);
    }
}
