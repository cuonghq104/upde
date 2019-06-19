package stp.cuonghq.upde.services.networks;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import stp.cuonghq.upde.data.models.LoginData;
import stp.cuonghq.upde.data.models.LoginRequest;
import stp.cuonghq.upde.data.models.LogoutRequest;
import stp.cuonghq.upde.data.models.Response;

import static stp.cuonghq.upde.commons.Constants.ApiConstant.HOST_LOGIN_PATH;
import static stp.cuonghq.upde.commons.Constants.ApiConstant.HOST_LOGOUT_PATH;
import static stp.cuonghq.upde.commons.Constants.ApiConstant.HOST_CHECK_TOKEN_PATH;
import static stp.cuonghq.upde.commons.Constants.ApiConstant.SALEPOINT_LOGIN_PATH;

public interface AccountServices {
    @POST(HOST_LOGIN_PATH)
    Observable<Response<LoginData>> login(@Body LoginRequest request);

    @POST(HOST_LOGOUT_PATH)
    Observable<Response> logOut(@Body LogoutRequest request);

    @POST(HOST_CHECK_TOKEN_PATH)
    Observable<Response> checkTokenStatus();

    @POST(SALEPOINT_LOGIN_PATH)
    Observable<Response<LoginData>> loginAsHost(@Body LoginRequest request);

}
