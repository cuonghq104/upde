package stp.cuonghq.upde.screen.splash;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import stp.cuonghq.upde.commons.ApiCallback;
import stp.cuonghq.upde.commons.AppContext;
import stp.cuonghq.upde.commons.AppSharePreferences;
import stp.cuonghq.upde.commons.Constants;
import stp.cuonghq.upde.commons.NetworkClient;
import stp.cuonghq.upde.commons.Utilities;
import stp.cuonghq.upde.data.repositories.AccountRepository;

class Presenter implements Contract.Presenter {

    private static final String TAG = Presenter.class.toString();

    Contract.View mView;
    AccountRepository mRepository;

    public void setView(Contract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getFirebaseToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            if (mView != null) {
                                mView.getFirebaseTokenFailed(task.getException().getLocalizedMessage());
                                return;
                            }
                        }

                        String token = task.getResult().getToken();
                        Log.d(TAG, "Firebase token: " + token);
                        AppSharePreferences.saveToSP(Constants.SharePreferenceConstants.FIREBASE_TOKEN, token);
                        if (mView != null) {
                            mView.getFirebaseTokenSuccess();
                        }
                    }
                });
    }

    @Override
    public void checkUserInformation() {
        String accessToken = AppSharePreferences.getStringFromSP(Constants.SharePreferenceConstants.ACCESS_TOKEN);
        Log.d(TAG, "Access token: " + accessToken);
        if (!accessToken.equals("")) {
            NetworkClient.initHeaderInstance(accessToken);
            if (mView != null) {
                mRepository = AccountRepository.getInstance();
                mRepository.checkTokenStatus(new ApiCallback() {
                    @Override
                    public void success(Object data, String msg) {
                        mView.informationValid();
                    }

                    @Override
                    public void failed(String msg) {
                        Utilities.showToast(AppContext.getInstance(), "Token expired");
                        mView.informationNotValid();
                    }
                });
            }
        } else {
            NetworkClient.initHeaderInstance("nigger homie");
            if (mView != null)
                // mView.informationValid();
                mView.informationNotValid();
        }
    }

}
