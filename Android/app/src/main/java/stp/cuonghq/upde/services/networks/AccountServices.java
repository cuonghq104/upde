package stp.cuonghq.upde.services.networks;


import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import stp.cuonghq.upde.commons.AvatarResponse;
import stp.cuonghq.upde.data.models.EditInfoRequest;
import stp.cuonghq.upde.data.models.LoginData;
import stp.cuonghq.upde.data.models.LoginRequest;
import stp.cuonghq.upde.data.models.LogoutRequest;
import stp.cuonghq.upde.data.models.Response;

import static stp.cuonghq.upde.commons.Constants.ApiConstant.HOST_LOGIN_PATH;
import static stp.cuonghq.upde.commons.Constants.ApiConstant.HOST_LOGOUT_PATH;
import static stp.cuonghq.upde.commons.Constants.ApiConstant.HOST_CHECK_TOKEN_PATH;
import static stp.cuonghq.upde.commons.Constants.ApiConstant.ROLE_PATH;
import static stp.cuonghq.upde.commons.Constants.ApiConstant.SALEPOINT_LOGIN_PATH;
import static stp.cuonghq.upde.commons.Constants.ApiConstant.UPDATE_IMAGE;
import static stp.cuonghq.upde.commons.Constants.ApiConstant.UPDATE_INFORMATION;

public interface AccountServices {
    @POST(SALEPOINT_LOGIN_PATH)
    Observable<Response<LoginData>> login(@Body LoginRequest request);

    @POST(HOST_LOGOUT_PATH)
    Observable<Response> logOut(@Body LogoutRequest request);

    @POST(HOST_CHECK_TOKEN_PATH)
    Observable<Response> checkTokenStatus();

    @POST(HOST_LOGIN_PATH)
    Observable<Response<LoginData>> loginAsHost(@Body LoginRequest request);

    @POST(UPDATE_INFORMATION)
    Observable<Response> editInfo(@Path(ROLE_PATH) String role, @Body EditInfoRequest request);

    @Multipart
    @POST(UPDATE_IMAGE)
    Observable<Response<AvatarResponse>> updateAvatar(@Part MultipartBody.Part filePart);
}
