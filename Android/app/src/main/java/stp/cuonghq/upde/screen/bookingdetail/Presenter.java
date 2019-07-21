package stp.cuonghq.upde.screen.bookingdetail;

import stp.cuonghq.upde.commons.ApiCallback;
import stp.cuonghq.upde.commons.AppSharePreferences;
import stp.cuonghq.upde.commons.BasePresenter;
import stp.cuonghq.upde.commons.Constants;
import stp.cuonghq.upde.commons.NetworkClient;
import stp.cuonghq.upde.data.models.ChangeStatusResponse;
import stp.cuonghq.upde.data.models.LoginData;
import stp.cuonghq.upde.data.repositories.TripRepository;

public class Presenter extends BasePresenter<BookingDetailActivity> implements Contract.Presenter {

    private Contract.View mView;
    private TripRepository mRepository;

    public Presenter() {
        mRepository = TripRepository.getInstance();
    }

    public void setView(Contract.View mView) {
        this.mView = mView;
    }

    @Override
    public void confirm(String idTrip, String note) {
        mView.doLoading();

        if (NetworkClient.getHeaderInstance() == null) {
            NetworkClient.initHeaderInstance(AppSharePreferences.getStringFromSP(Constants.SharePreferenceConstants.ACCESS_TOKEN));
        }

        LoginData data = AppSharePreferences.getFromSP(Constants.SharePreferenceConstants.DATA, LoginData.class);
        String idHost = (data != null) ? data.getId() : "";

        mRepository.confirmBooking(idHost, idTrip, note, new ApiCallback<ChangeStatusResponse>() {
            @Override
            public void success(ChangeStatusResponse data, String msg) {
                mView.confirmSuccess(data.getResp(), msg);
            }

            @Override
            public void failed(String msg) {
                mView.confirmFailed(msg);
            }
        });
    }

    @Override
    public void removeFromUnreadList(String idTrip) {

    }
}
