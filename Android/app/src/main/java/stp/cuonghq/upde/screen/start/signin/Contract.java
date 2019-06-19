package stp.cuonghq.upde.screen.start.signin;


class Contract {

    interface View {
        void doLogin();
        void loginSuccess(String msg, String type);
        void loginFailed(String msg);
        void updateEmail(String email);
        void updatePassword(String password);
    }

    interface Presenter {
        void login(String email, String password, String type);
        void emailChange(String email);
        void passwordChange(String password);
    }
}
