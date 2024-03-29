package stp.cuonghq.upde.commons;

import android.Manifest;
import android.content.Context;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by cuong.hq1 on 5/3/2019.
 */

public class Constants {

    public static final String[] PERMISSION_NEEDED = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public static final int BOOKING_REQUEST_CODE = 1000;
    public static final int CONFIRM_REQUEST_CODE = 1001;
    public static final int PENDING_INTENT_FCM_REQUEST_CODE = 1003;
    public static final int NOTIFICATION_NEW_BOOKING = 1004;
    public static final int STATISTIC_REQUEST_CODE = 1005;

    public static final String CHANNEL_ID = "UPDE";
    public static final String CHANNEL_NAME = "UPDE_CHANNEL";
    public static final String CHANNEL_DISCRIPTION = "UPDE_DESCRIPTION";

    public class DateAndTime {
        public static final String ORIGINAL_DATE = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        public static final String APP_DATE = "hh:mm / dd-MM-yyyy";
        public static final String TYPE_DAY = "day";
        public static final String TYPE_YEAR = "year";
        public static final String TYPE_MONTH = "month";
        public static final String TYPE_WEEK = "week";
    }

    public class Extras {
        public static final String BOOKING = "booking";
        public static final String OPERATION = "op";
        public static final int COMPLETE = 1;
        public static final int CONFIRM = -1;
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final int ACCEPT_BY_ANOTHER_USER = 3;
        public static final int SUCCESS = 2;
        public static final String RESULT = "result";
    }

    public class SharePreferenceConstants {
        public static final String NAME = "UPDE";
        public static final int MODE = Context.MODE_PRIVATE;
        public static final String FIREBASE_TOKEN = "fbase_token";
        public static final String DATA = "data";
        public static final String ACCESS_TOKEN = "access_token";
        public static final String STATE = "state";
        public static final String LOGIN_TYPE = "login_type";
        public static final String EMAIL = "login_email";
        public static final String PASSWORD = "login_password";
        public static final String STATISTIC = "statistic";
    }

    public static class ApiConstant {
//        static final String BASE_URL = "https://server.upit.asia/api/";
        public static final String BASE_URL = "https://serverdev.upit.asia/api/";
        // public static final String BASE_URL = "http://1+07.113.186.171:3200/api/";

        public static final String HOST = "host";
        public static final String HOST_PLURAL = "hosts";
        public static final String TRIP = "trip";
        public static final String SALE_POINT = "salepoint";
        public static final String SALE_POINT_PLURAL = "salepoints";

        public static final String HEADER_AUTHORIZATION = "access_token";
        private static final String HOST_PATH = HOST + "/";
        private static final String HOST_PLURAL_PATH = HOST_PLURAL + "/";
        private static final String TRIP_PATH = TRIP + "/";
        private static final String SALE_POINT_PATH = SALE_POINT + "/";
        private static final String SALE_POINT_PLURAL_PATH = SALE_POINT_PLURAL + "/";

        private static final String LOGIN_PATH = "loginAdvance";
        private static final String LOGOUT_PATH = "logout";
        private static final String CHECK_TOKEN_PATH = "checkToken";
        public static final String EDIT_INFO_PATH = "editInfo";
        public static final String CHANGE_AVATAR_PATH = "changeAvatar";
        public static final String CHANGE_PASSWORD_PATH = "changepassword";

        public static final String SALEPOINT_LOGIN_PATH = SALE_POINT_PATH + LOGIN_PATH;
        public static final String HOST_CHECK_TOKEN_PATH = CHECK_TOKEN_PATH;

        public static final String ROLE_PATH = "role";
        public static final String LOG_IN = "{" + ROLE_PATH + "}/" + LOGIN_PATH;
        public static final String LOG_OUT = "{" + ROLE_PATH + "}/" + LOGOUT_PATH;
        public static final String UPDATE_INFORMATION = "{" + ROLE_PATH + "}/" + EDIT_INFO_PATH;
        public static final String UPDATE_IMAGE = "{" + ROLE_PATH + "}/" + CHANGE_AVATAR_PATH;
        public static final String CHANGE_PASSWORD = "{" + ROLE_PATH + "}/" + CHANGE_PASSWORD_PATH;

        public static final String IMAGE_PATH = "image/";
        public static final String GET_APP_BOOKING_CREATE = "getAllTripCreate";
        public static final String GET_APP_BOOKING_ACCEPT = "getAllTripAccept";
        public static final String GET_APP_BOOKING_COMPLETE = "getAllTripComplete";
        public static final String GET_APP_BOOKING_COMPLETE_BY_TIME = "getAllTripCompleteByTime";
        public static final String GET_HOTEL_LINK_OF_HOST = "getHotelLinks";

        private static final String ACCEPT_BOOKING = "acceptBookingByHost";
        private static final String COMPLETE_BOOKING = "confirmCompleteBookingByHost";
        public static final String TRIP_ACCEPT_BOOKING = TRIP_PATH + ACCEPT_BOOKING;
        public static final String TRIP_COMPLETE_BOOKING = TRIP_PATH + COMPLETE_BOOKING;

        private static final String GET_ALL_PRICE = "getAllPrice";

        public static final String SALE_POINT_GET_STATISTIC = SALE_POINT_PATH + GET_ALL_PRICE;
        public static final String SALE_POINT_GET_STATISTIC_ALL_BOOKING_COMPLETE = SALE_POINT_PATH + GET_APP_BOOKING_COMPLETE_BY_TIME;
        public static final String GET_FULL_HOTEL_LINK_OF_HOST = SALE_POINT_PATH + GET_HOTEL_LINK_OF_HOST;
        public static final String BOOKING_TYPE = "booking_type";
        public static final String HOST_BOOKING_CREATED = HOST_PATH + "{" + BOOKING_TYPE + "}";

        public static class Model {
            public static final String MESSAGE = "message";
            public static final String CODE = "code";
            public static final String DATA = "data";
        }
    }

    public static class DatabaseConstant {

        public static final String DB_NAME = "upde_dtb";

        public static final String TABLE_TRIP = "tbl_trip";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_ID_TRIP = "id_trip";
    }

    public static final String LOGIN_AS_SUPPLIER_TYPE = "supplier"; // Nhà cung cấp, layout nhận noti đặt xe
    public static final String LOGIN_AS_HOST_TYPE = "host";          // Chủ nhà, layout thống kê

    public static String imageUrl(String imageName) {
        String loginType = AppSharePreferences.getStringFromSP(SharePreferenceConstants.LOGIN_TYPE);

        StringBuilder sb = new StringBuilder(ApiConstant.BASE_URL);
        sb.append(ApiConstant.IMAGE_PATH);
        sb.append((StringUtils.equals(loginType, LOGIN_AS_SUPPLIER_TYPE) ? ApiConstant.HOST_PLURAL_PATH : ApiConstant.SALE_POINT_PLURAL_PATH));
        sb.append(imageName);
        return sb.toString();
    }
}
