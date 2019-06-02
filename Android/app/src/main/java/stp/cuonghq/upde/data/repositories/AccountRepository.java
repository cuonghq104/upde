package stp.cuonghq.upde.data.repositories;

import stp.cuonghq.upde.commons.ApiCallback;
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
}
