package stp.cuonghq.upde.screen.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

import stp.cuonghq.upde.commons.ApiCallback;
import stp.cuonghq.upde.commons.AppSharePreferences;
import stp.cuonghq.upde.commons.Constants;
import stp.cuonghq.upde.commons.Utilities;
import stp.cuonghq.upde.data.models.LoginData;
import stp.cuonghq.upde.data.repositories.AccountRepository;

public class Presenter implements Contract.Presenter {

    private Contract.View mView;

    private AccountRepository mRepository;

    public Presenter() {
        mRepository = AccountRepository.getInstance();
    }

    public void setView(Contract.View mView) {
        this.mView = mView;
    }

    @Override
    public void logOut() {

        String firebaseToken = AppSharePreferences.getStringFromSP(Constants.SharePreferenceConstants.FIREBASE_TOKEN);

        mRepository.logOut(firebaseToken, new ApiCallback() {
            @Override
            public void success(Object data, String msg) {
                AppSharePreferences.removeFromSP(
                        Constants.SharePreferenceConstants.ACCESS_TOKEN,
                        Constants.SharePreferenceConstants.DATA
                );
                mView.logOutSuccess(msg);
            }

            @Override
            public void failed(String msg) {
                AppSharePreferences.removeFromSP(
                        Constants.SharePreferenceConstants.ACCESS_TOKEN,
                        Constants.SharePreferenceConstants.DATA
                );
                mView.logOutSuccess(msg);
            }
        });
    }

    @Override
    public LoginData getUserData() {
        LoginData loginData = AppSharePreferences.getFromSP(Constants.SharePreferenceConstants.DATA, LoginData.class);
        return loginData;
    }
}
