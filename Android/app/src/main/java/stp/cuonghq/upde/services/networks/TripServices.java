package stp.cuonghq.upde.services.networks;


import io.reactivex.Observable;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import stp.cuonghq.upde.data.models.BookingChangeStatus;
import stp.cuonghq.upde.data.models.BookingList;
import stp.cuonghq.upde.data.models.BookingResp;
import stp.cuonghq.upde.data.models.ChangeStatusResponse;
import stp.cuonghq.upde.data.models.Response;

import static stp.cuonghq.upde.commons.Constants.ApiConstant.BOOKING_TYPE;
import static stp.cuonghq.upde.commons.Constants.ApiConstant.HOST_BOOKING_CREATED;
import static stp.cuonghq.upde.commons.Constants.ApiConstant.TRIP_ACCEPT_BOOKING;
import static stp.cuonghq.upde.commons.Constants.ApiConstant.TRIP_COMPLETE_BOOKING;

public interface TripServices {

    @POST(HOST_BOOKING_CREATED)
    Observable<Response<BookingList>> getBookingList(@Path(BOOKING_TYPE) String type);

    @POST(TRIP_ACCEPT_BOOKING)
    Observable<Response<ChangeStatusResponse>> confirmBooking(@Body BookingChangeStatus body);

    @POST(TRIP_COMPLETE_BOOKING)
    Observable<Response<ChangeStatusResponse>> completeBooking(@Body BookingChangeStatus body);


}
