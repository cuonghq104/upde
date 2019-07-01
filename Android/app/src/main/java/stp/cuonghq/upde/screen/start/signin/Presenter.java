package stp.cuonghq.upde.screen.start.signin;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

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
    public void login(final String email, final String password, final String type) {
        final String emailValidated = email.trim();
        final String passwordValidated = password.trim();
        String token = AppSharePreferences.getStringFromSP(Constants.SharePreferenceConstants.FIREBASE_TOKEN);
        mView.doLogin();
        String role = (StringUtils.equals(type, Constants.LOGIN_AS_SUPPLIER_TYPE)) ? Constants.ApiConstant.HOST : Constants.ApiConstant.SALE_POINT;
        mRepository.login(role, emailValidated, passwordValidated, token, new ApiCallback<LoginData>() {
            @Override
            public void success(LoginData data, String msg) {
                AppSharePreferences.saveToSP(Constants.SharePreferenceConstants.DATA, data);
                AppSharePreferences.saveToSP(Constants.SharePreferenceConstants.ACCESS_TOKEN, data.getTokenId());
                AppSharePreferences.saveToSP(Constants.SharePreferenceConstants.LOGIN_TYPE, type);
                AppSharePreferences.saveToSP(Constants.SharePreferenceConstants.EMAIL, emailValidated);
                AppSharePreferences.saveToSP(Constants.SharePreferenceConstants.PASSWORD, passwordValidated);
                NetworkClient.initHeaderInstance(data.getTokenId());
                mView.loginSuccess(msg, type);
            }

            @Override
            public void failed(String msg) {
                mView.loginFailed(msg);
            }
        });

    }


    @OnLifecycleEvent(value = Lifecycle.Event.ON_CREATE)
    protected void onCreate() {
//        String email = viewStateBundle.getString(BUNDLE_KEY_EMAIL);
//        String password = viewStateBundle.getString(BUNDLE_KEY_PASSWORD);
        String email = AppSharePreferences.getStringFromSP(Constants.SharePreferenceConstants.EMAIL);
        String password = AppSharePreferences.getStringFromSP(Constants.SharePreferenceConstants.PASSWORD);
        Log.d("AppSharePreferences: ", email + " " + password);
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
