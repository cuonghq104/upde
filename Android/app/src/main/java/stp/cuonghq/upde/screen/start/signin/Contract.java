package stp.cuonghq.upde.screen.start.signin;


class Contract {

    interface View {
        void doLogin();
        void loginSuccess(String msg);
        void loginFailed(String msg);
        void updateEmail(String email);
        void updatePassword(String password);
    }

    interface Presenter {
        void login(String email, String password);
        void emailChange(String email);
        void passwordChange(String password);
    }
}
