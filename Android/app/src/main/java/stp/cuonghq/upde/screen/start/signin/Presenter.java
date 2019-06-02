package stp.cuonghq.upde.screen.start.signin;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;

import stp.cuonghq.upde.commons.ApiCallback;
import stp.cuonghq.upde.commons.AppSharePreferences;
import stp.cuonghq.upde.commons.BaseContract;
import stp.cuonghq.upde.commons.BasePresenter;
import stp.cuonghq.upde.commons.Constants;
import stp.cuonghq.upde.commons.NetworkClient;
import stp.cuonghq.upde.commons.Utilities;
import stp.cuonghq.upde.data.models.LoginData;
import stp.cuonghq.upde.data.repositories.AccountRepository;

class Presenter extends BasePresenter<SignInFragment> implements Contract.Presenter{

    public static final String BUNDLE_KEY_EMAIL = "email";
    public static final String BUNDLE_KEY_PASSWORD = "password";
    private Bundle viewStateBundle = getStateBundle();

    Contract.View mView;

    AccountRepository mRepository;

    Presenter() {
        mRepository = AccountRepository.getInstance();
    }

    public void setView(Contract.View mView) {
        this.mView = mView;
    }

    @Override
    public void login(String email, String password) {
        email = email.trim();
        password = password.trim();
        mView.doLogin();

        String token = AppSharePreferences.getStringFromSP(Constants.SharePreferenceConstants.FIREBASE_TOKEN);
        mRepository.login(email, password, token, new ApiCallback<LoginData>() {
            @Override
            public void success(LoginData data, String msg) {
                AppSharePreferences.saveToSP(Constants.SharePreferenceConstants.DATA, data);
                AppSharePreferences.saveToSP(Constants.SharePreferenceConstants.ACCESS_TOKEN, data.getTokenId());
                NetworkClient.initHeaderInstance(data.getTokenId());

                mView.loginSuccess(msg);
            }

            @Override
            public void failed(String msg) {
                mView.loginFailed(msg);
            }
        });
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_CREATE)
    protected void onCreate() {
        String email = viewStateBundle.getString(BUNDLE_KEY_EMAIL);
        String password = viewStateBundle.getString(BUNDLE_KEY_PASSWORD);
        if (email != null) {
            getView().updateEmail(email);
        }
        if (password != null) {
            getView().updatePassword(password);
        }
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_DESTROY)
    protected void onDestroy() {

    }
    @Override
    public void emailChange(String email) {
        viewStateBundle.putString(BUNDLE_KEY_EMAIL, email);
    }

    @Override
    public void passwordChange(String password) {
        viewStateBundle.putString(BUNDLE_KEY_PASSWORD, password);
    }

    @Override
    public void onPresenterDestroy() {
        super.onPresenterDestroy();
    }
}
