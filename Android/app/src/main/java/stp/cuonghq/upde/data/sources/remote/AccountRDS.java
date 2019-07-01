package stp.cuonghq.upde.data.sources.remote;


import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import stp.cuonghq.upde.commons.ApiCallback;
import stp.cuonghq.upde.commons.ApiService;
import stp.cuonghq.upde.commons.AppSharePreferences;
import stp.cuonghq.upde.commons.AvatarResponse;
import stp.cuonghq.upde.commons.Constants;
import stp.cuonghq.upde.commons.NetworkClient;
import stp.cuonghq.upde.data.models.ChangePasswordRequest;
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
    public void login(String role, String email, String password, String token, ApiCallback<LoginData> callback) {
        LoginRequest request = new LoginRequest(email, password, token);
        Log.d("AccountRDS", token);
        loginResponseObservable(role, request).subscribeWith(ApiService.disposableObserver(callback));
    }

//    @Override
//    public void loginAsHost(String email, String password, String token, ApiCallback<LoginData> callback) {
//        LoginRequest request = new LoginRequest(email, password, token);
//        loginAsHostResponseObservable(request).subscribeWith(ApiService.disposableObserver(callback));
//    }

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

    @Override
    public void changeProfileImage(String fileType, String image, ApiCallback callback) {
        changeAvatarObservable(fileType, image).subscribeWith(ApiService.disposableObserver(callback));
    }

    @Override
    public void changePassword(String oldPassword, String newPassword, ApiCallback callback) {
        changePasswordObservable(oldPassword, newPassword).subscribeWith(ApiService.disposableObserver(callback));
    }

    Observable<Response> changePasswordObservable(String oldPass, String newPass) {
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setNewPassword(newPass);
        request.setOldPassword(oldPass);

        String role = AppSharePreferences.getStringFromSP(Constants.SharePreferenceConstants.LOGIN_TYPE);
        String rolePath = (StringUtils.equals(role, Constants.LOGIN_AS_HOST_TYPE)) ? Constants.ApiConstant.SALE_POINT : Constants.ApiConstant.HOST;

        return NetworkClient.getHeaderInstance()
                .create(AccountServices.class)
                .changePassword(rolePath, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    Observable<Response<AvatarResponse>> changeAvatarObservable(String fileType, String image) {
        String role = AppSharePreferences.getStringFromSP(Constants.SharePreferenceConstants.LOGIN_TYPE);
        String rolePath = (StringUtils.equals(role, Constants.LOGIN_AS_HOST_TYPE)) ? Constants.ApiConstant.SALE_POINT_PLURAL : Constants.ApiConstant.HOST;
        MultipartBody.Part avatar = null;
        if (image != null) {
            File file = new File(image);
            avatar = MultipartBody.Part.createFormData("salepoint", file.getName(), RequestBody.create(MediaType.parse(fileType), file));
        }

        Log.d("ab", "abc");
        return NetworkClient.getHeaderInstance()
                .create(AccountServices.class)
                .updateAvatar(rolePath, avatar)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    Observable<Response> editInformationObservable(EditInfoRequest request) {
        String role = AppSharePreferences.getStringFromSP(Constants.SharePreferenceConstants.LOGIN_TYPE);
        String rolePath = (StringUtils.equals(role, Constants.LOGIN_AS_HOST_TYPE)) ? Constants.ApiConstant.SALE_POINT : Constants.ApiConstant.HOST;

        return NetworkClient.getHeaderInstance()
                .create(AccountServices.class)
                .editInfo(rolePath, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    Observable<Response<LoginData>> loginResponseObservable(String role, LoginRequest request) {
        return NetworkClient.getRetrofit()
                .create(AccountServices.class)
                .login(role, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    Observable<Response> logOutObservable(LogoutRequest request) {
        String role = AppSharePreferences.getStringFromSP(Constants.SharePreferenceConstants.LOGIN_TYPE);
        String rolePath = (StringUtils.equals(role, Constants.LOGIN_AS_HOST_TYPE)) ? Constants.ApiConstant.SALE_POINT : Constants.ApiConstant.HOST;

        return NetworkClient.getHeaderInstance()
                .create(AccountServices.class)
                .logOut(rolePath, request)
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

//    Observable<Response<LoginData>> loginAsHostResponseObservable(LoginRequest request) {
//        return NetworkClient.getRetrofit()
//                .create(AccountServices.class)
//                .login(request)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }

}
