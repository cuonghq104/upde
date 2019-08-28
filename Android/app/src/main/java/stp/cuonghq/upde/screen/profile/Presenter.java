package stp.cuonghq.upde.screen.profile;

import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

import stp.cuonghq.upde.commons.ApiCallback;
import stp.cuonghq.upde.commons.AppSharePreferences;
import stp.cuonghq.upde.commons.AvatarResponse;
import stp.cuonghq.upde.commons.BasePresenter;
import stp.cuonghq.upde.commons.Constants;
import stp.cuonghq.upde.data.models.LoginData;
import stp.cuonghq.upde.data.repositories.AccountRepository;

public class Presenter extends BasePresenter<ProfileFragment> implements Contract.Presenter {


    private AccountRepository mRepository;

    public Presenter() {
        mRepository = AccountRepository.getInstance();
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
                getView().logOutSuccess(msg);
            }

            @Override
            public void failed(String msg) {
                AppSharePreferences.removeFromSP(
                        Constants.SharePreferenceConstants.ACCESS_TOKEN,
                        Constants.SharePreferenceConstants.DATA
                );
                getView().logOutSuccess(msg);
            }
        });
    }

    @Override
    public LoginData getUserData() {
        return AppSharePreferences.getFromSP(Constants.SharePreferenceConstants.DATA, LoginData.class);
    }

    @Override
    public String getLoginType() {
        return AppSharePreferences.getStringFromSP(Constants.SharePreferenceConstants.LOGIN_TYPE);
    }

    @Override
    public void changeProfileImage(String fileType, String filePath) {
        getView().loading();
        AccountRepository.getInstance().changeAvatar(fileType, filePath, new ApiCallback<AvatarResponse>() {
            @Override
            public void success(AvatarResponse data, String msg) {
                getView().changeProfileImageSuccess(data);
            }

            @Override
            public void failed(String msg) {
                getView().changeProfileImageFailed(msg);
            }
        });
    }


}
