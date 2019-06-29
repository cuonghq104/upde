package stp.cuonghq.upde.data.repositories;

import stp.cuonghq.upde.commons.ApiCallback;
import stp.cuonghq.upde.commons.AvatarResponse;
import stp.cuonghq.upde.data.models.LoginData;
import stp.cuonghq.upde.data.sources.AccountDatasource;
import stp.cuonghq.upde.data.sources.remote.AccountRDS;

public class AccountRepository {

    private AccountRDS mRds;

    private static AccountRepository sInstance;

    private AccountRepository() {
        this.mRds = AccountRDS.getInstance();
    }

    public static AccountRepository getInstance() {
        if (sInstance == null) {
            sInstance = new AccountRepository();
        }
        return sInstance;
    }

    public void login(String email, String password, String token, ApiCallback<LoginData> callback) {
        if (mRds != null) {
            mRds.login(email, password, token, callback);
        }
    }

    public void loginAsHost(String email, String password, ApiCallback<LoginData> callback) {
        if (mRds != null) {
            mRds.loginAsHost(email, password, callback);
        }
    }

    public void logOut(String firebaseToken, ApiCallback callback) {
        if (mRds != null) {
            mRds.logOut(firebaseToken, callback);
        }
    }

    public void checkTokenStatus(ApiCallback callback) {
        if (mRds != null) {
            mRds.checkTokenStatus(callback);
        }
    }

    public void editInfo(String phone, String name, ApiCallback callback) {
        if (mRds != null) {
            mRds.editInformation(phone, name, callback);
        }
    }

    public void changeAvatar(String fileType, String image, ApiCallback<AvatarResponse> callback) {
        if (mRds != null) {
            mRds.changeProfileImage(fileType, image, callback);
        }
    }
}
