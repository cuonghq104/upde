package stp.cuonghq.upde.screen.splash;

class Contract {

    interface View {
        void getFirebaseTokenSuccess();
        void getFirebaseTokenFailed(String msg);

        void informationValid();
        void informationNotValid();

    }

    interface Presenter {
        void getFirebaseToken();
        void checkUserInformation();
    }
}
