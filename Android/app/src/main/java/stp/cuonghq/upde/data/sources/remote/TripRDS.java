package stp.cuonghq.upde.data.sources.remote;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import stp.cuonghq.upde.commons.ApiCallback;
import stp.cuonghq.upde.commons.ApiService;
import stp.cuonghq.upde.commons.NetworkClient;
import stp.cuonghq.upde.data.models.BookingChangeStatus;
import stp.cuonghq.upde.data.models.BookingList;
import stp.cuonghq.upde.data.models.BookingResp;
import stp.cuonghq.upde.data.models.ChangeStatusResponse;
import stp.cuonghq.upde.data.models.ItemHome;
import stp.cuonghq.upde.data.models.ListItemHome;
import stp.cuonghq.upde.data.models.ListResponse;
import stp.cuonghq.upde.data.models.Response;
import stp.cuonghq.upde.data.models.StatisticGetAllPriceRequest;
import stp.cuonghq.upde.data.models.StatisticGetAllPriceResponce;
import stp.cuonghq.upde.data.models.StatisticTripCompleteRequest;
import stp.cuonghq.upde.data.sources.TripDatasource;
import stp.cuonghq.upde.services.networks.TripServices;

import static stp.cuonghq.upde.commons.Constants.ApiConstant.GET_APP_BOOKING_ACCEPT;
import static stp.cuonghq.upde.commons.Constants.ApiConstant.GET_APP_BOOKING_COMPLETE;
import static stp.cuonghq.upde.commons.Constants.ApiConstant.GET_APP_BOOKING_CREATE;

public class TripRDS implements TripDatasource.RDS {

    public static TripRDS sInstance;

    public static TripRDS getInstance() {
        if (sInstance == null) {
            sInstance = new TripRDS();
        }
        return sInstance;
    }

    @Override
    public void getBookingList(ApiCallback<BookingList> callback) {
        bookingListObservable().subscribeWith(ApiService.disposableObserver(callback));
    }

    @Override
    public void getConfirmList(ApiCallback<BookingList> callback) {
        confirmListObservable().subscribeWith(ApiService.disposableObserver(callback));
    }

    @Override
    public void getCompleteList(ApiCallback<BookingList> callback) {
        completeListObservable().subscribeWith(ApiService.disposableObserver(callback));
    }

    @Override
    public void confirmBooking(String idHost, String idTrip, ApiCallback<ChangeStatusResponse> callback) {
        BookingChangeStatus body = new BookingChangeStatus(idHost, idTrip);
        confirmBookingObservable(body).subscribeWith(ApiService.disposableObserver(callback));
    }

    @Override
    public void completeBooking(String idHost, String idTrip, ApiCallback<ChangeStatusResponse> callback) {
        BookingChangeStatus body = new BookingChangeStatus(idHost, idTrip);
        completeBookingObservable(body).subscribeWith(ApiService.disposableObserver(callback));
    }

    @Override
    public void getAllPrice(String type, int page, ApiCallback<StatisticGetAllPriceResponce> callback) {
        StatisticGetAllPriceRequest body = new StatisticGetAllPriceRequest(type, page);
        getAllPriceObservable(body).subscribeWith(ApiService.disposableObserver(callback));
    }

    @Override
    public void getAllTripCompleteByTimeObservale(String time_begin, String time_end, long page, ApiCallback<BookingList> callback) {
        StatisticTripCompleteRequest body = new StatisticTripCompleteRequest(time_begin, time_end , page);
        getAllTripCompleteByTimeObservale(body).subscribeWith(ApiService.disposableObserver(callback));
    }

    @Override
    public void getHotelLinks(ApiCallback<ListItemHome> callback) {
        getHotelLink().subscribeWith(ApiService.disposableObserver(callback));
    }


    private Observable<Response<ChangeStatusResponse>> completeBookingObservable(BookingChangeStatus body) {
        return NetworkClient
                .getHeaderInstance()
                .create(TripServices.class)
                .completeBooking(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    private Observable<Response<ChangeStatusResponse>> confirmBookingObservable(BookingChangeStatus body) {
        return NetworkClient.getHeaderInstance()
                .create(TripServices.class)
                .confirmBooking(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Response<BookingList>> completeListObservable() {
        return NetworkClient
                .getHeaderInstance()
                .create(TripServices.class)
                .getBookingList(GET_APP_BOOKING_COMPLETE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Response<BookingList>> bookingListObservable() {
        return NetworkClient
                .getHeaderInstance()
                .create(TripServices.class)
                .getBookingList(GET_APP_BOOKING_CREATE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Response<BookingList>> confirmListObservable() {
        return NetworkClient
                .getHeaderInstance()
                .create(TripServices.class)
                .getBookingList(GET_APP_BOOKING_ACCEPT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }


    //new
    private Observable<Response<StatisticGetAllPriceResponce>> getAllPriceObservable(StatisticGetAllPriceRequest body){
        return NetworkClient
                .getHeaderInstance()
                .create(TripServices.class)
                .getAllPrice(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Response<BookingList>> getAllTripCompleteByTimeObservale(StatisticTripCompleteRequest body){
        return NetworkClient
                .getHeaderInstance()
                .create(TripServices.class)
                .getAllTripCompleteByTime(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Response<ListItemHome>> getHotelLink(){
        return NetworkClient
                .getHeaderInstance()
                .create(TripServices.class)
                .getHotelLink()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
