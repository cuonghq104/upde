package stp.cuonghq.upde.screen.home.complete;

import android.content.Context;

import stp.cuonghq.upde.commons.ApiCallback;
import stp.cuonghq.upde.data.models.BookingList;
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
    public void getCompleteList() {
        mView.onLoading();
        mRepository.getCompleteList(new ApiCallback<BookingList>() {
            @Override
            public void success(BookingList data, String msg) {
                mView.getCompleteListSuccess(data);
            }

            @Override
            public void failed(String msg) {
                mView.getCompleteListFailed(msg);
            }
        });
    }
}
