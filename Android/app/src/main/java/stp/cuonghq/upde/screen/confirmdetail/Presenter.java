package stp.cuonghq.upde.screen.confirmdetail;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import stp.cuonghq.upde.commons.ApiCallback;
import stp.cuonghq.upde.commons.AppSharePreferences;
import stp.cuonghq.upde.commons.Constants;
import stp.cuonghq.upde.commons.Utilities;
import stp.cuonghq.upde.data.models.BookingResp;
import stp.cuonghq.upde.data.models.ChangeStatusResponse;
import stp.cuonghq.upde.data.models.LoginData;
import stp.cuonghq.upde.data.repositories.TripRepository;

public class Presenter implements Contract.Presenter {

    private Contract.View mView;
    private TripRepository mRepository;

    public Presenter() {
        mRepository = TripRepository.getInstance();
    }

    public void setView(Contract.View mView) {
        this.mView = mView;
    }

    @Override
    public void completeBooking(String idTrip) {
        mView.doLoading();

//        SharedPreferences sp = Utilities.getSP(mContext);
//        String json = sp.getString(Constants.SharePreferenceConstants.DATA, "");
//        LoginData data = new Gson().fromJson(json, LoginData.class);

        LoginData data = AppSharePreferences.getFromSP(Constants.SharePreferenceConstants.DATA, LoginData.class);
        String idHost = (data != null) ? data.getId() : "";

        mRepository.completeBooking(idHost, idTrip, new ApiCallback<ChangeStatusResponse>() {
            @Override
            public void success(ChangeStatusResponse data, String msg) {
                mView.completeSuccess(data.getResp(), msg);
            }

            @Override
            public void failed(String msg) {
                mView.completeFailed(msg);
            }
        });
    }
}
