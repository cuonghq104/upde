package stp.cuonghq.upde.screen.statistic;

import stp.cuonghq.upde.commons.ApiCallback;
import stp.cuonghq.upde.commons.BasePresenter;
import stp.cuonghq.upde.data.models.BookingList;
import stp.cuonghq.upde.data.models.StatisticGetAllPriceRequest;
import stp.cuonghq.upde.data.models.StatisticGetAllPriceResponce;
import stp.cuonghq.upde.data.repositories.TripRepository;

public class Presenter extends BasePresenter<StatisticFragment> implements Contract.Presenter {

    private TripRepository mRepository;

    public Presenter() {
        mRepository = TripRepository.getInstance();
    }

    @Override
    public void getStatistic(String type , int page) {
        mRepository.getAllPrice(type,page,new ApiCallback<StatisticGetAllPriceResponce>() {
            @Override
            public void success(StatisticGetAllPriceResponce data, String msg) {
                getView().getStatisticSuccess(data);
            }

            @Override
            public void failed(String msg) {
                getView().getStatisticFailed(msg);
            }
        });
    }

    @Override
    public void getCompleteList(String time_begin,String time_end, long page) {

        mRepository.getAllTripCompleteByTimeObservale(time_begin, time_end, page, new ApiCallback<BookingList>() {
            @Override
            public void success(BookingList data, String msg) {
                getView().getCompleteListSuccess(data);
            }

            @Override
            public void failed(String msg) {
                getView().getCompleteListFailed(msg);
            }
        });
    }
}
