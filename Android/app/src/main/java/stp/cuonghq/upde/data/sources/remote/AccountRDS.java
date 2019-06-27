package stp.cuonghq.upde.data.sources.remote;


import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import stp.cuonghq.upde.commons.ApiCallback;
import stp.cuonghq.upde.commons.ApiService;
import stp.cuonghq.upde.commons.NetworkClient;
import stp.cuonghq.upde.data.models.EditInfoRequest;
import stp.cuonghq.upde.data.models.LoginData;
import stp.cuonghq.upde.data.models.LoginRequest;
import stp.cuonghq.upde.data.models.LogoutRequest;
import stp.cuonghq.upde.data.models.Response;
import stp.cuonghq.upde.data.sources.AccountDatasource;
import stp.cuonghq.upde.services.networks.AccountServices;

public class AccountRDS implements AccountDatasource.RDS {

    private static AccountRDS sInstance;

    public static AccountRDS getInstance() {
        if (sInstance == null) {
            sInstance = new AccountRDS();
        }
        return sInstance;
    }

    @Override
    public void login(String email, String password, String token, ApiCallback<LoginData> callback) {
        LoginRequest request = new LoginRequest(email, password, token);
        Log.d("AccountRDS", token);
        loginResponseObservable(request).subscribeWith(ApiService.disposableObserver(callback));
    }

    @Override
    public void loginAsHost(String email, String password, ApiCallback<LoginData> callback) {
        LoginRequest request = new LoginRequest(email, password);
        loginAsHostResponseObservable(request).subscribeWith(ApiService.disposableObserver(callback));
    }

    @Override
    public void logOut(String token, ApiCallback callback) {
        LogoutRequest request = new LogoutRequest();
        request.setToken(token);

        logOutObservable(request).subscribeWith(ApiService.disposableObserver(callback));
    }

    @Override
    public void checkTokenStatus(ApiCallback callback) {
        checkTokenStatusObservable().subscribeWith(ApiService.disposableObserver(callback));
    }

    @Override
    public void editInformation(String phone, String name, ApiCallback callback) {
        EditInfoRequest request = new EditInfoRequest();
        request.setName(name);
        request.setPhoneNumber(phone);
        editInformationObservable(request).subscribeWith(ApiService.disposableObserver(callback));
    }

    Observable<Response> editInformationObservable(EditInfoRequest request) {
        return NetworkClient.getHeaderInstance()
                .create(AccountServices.class)
                .editInfo(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    Observable<Response<LoginData>> loginResponseObservable(LoginRequest request) {
        return NetworkClient.getRetrofit()
                .create(AccountServices.class)
                .login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    Observable<Response> logOutObservable(LogoutRequest request) {
        return NetworkClient.getHeaderInstance()
                .create(AccountServices.class)
                .logOut(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    Observable<Response> checkTokenStatusObservable() {
        return NetworkClient.getHeaderInstance()
                .create(AccountServices.class)
                .checkTokenStatus()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    Observable<Response<LoginData>> loginAsHostResponseObservable(LoginRequest request) {
        return NetworkClient.getRetrofit()
                .create(AccountServices.class)
                .loginAsHost(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
