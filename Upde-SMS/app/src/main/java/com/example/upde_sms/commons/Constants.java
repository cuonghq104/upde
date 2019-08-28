package com.example.upde_sms.commons;

import android.Manifest;

public class Constants {

    public static final String[] PERMISSION_NEEDED = new String[]{
            Manifest.permission.SEND_SMS
    };

    public static final int REQUEST_PERMISSION_CODE = 1111;

    public class SMSTemplete {
        public static final String BOOKING_TYPE = "booking";
        public static final String CONFIRM_TYPE = "confirm";
        public static final String COMPLETE_TYPE = "complete";
        public static final String CANCEL_TYPE = "cancel";

        public static final String BOOKING = "Upit.asia: Tóm tắt chuyến xe đã được đặt thành công. %s" +
                "\nMã chuyến: %s. " +
                "\nĐịa chỉ: %s. " +
                "\nTổng cộng: %s hành khách. " +
                "\nHotline: %s";

        public static final String CONFIRM = "Upit.asia: Tóm tắt thông tin lái xe." +
                "\nMã chuyến: %s. " +
                "\nTên tài xế: %s. " +
                "\nSĐT: %s. " +
                "\nBiển số xe: %s. " +
                "\nLoại xe: %s. " +
                "\nHotline: %s.";

        public static final String COMPLETE = "Upit.asia: Tóm tắt hoàn thành chuyến xe" +
                "\nMã chuyến: %s. " +
                "\nMọi thông tin doanh thu vui lòng kiểm tra tại https://partner.upit.asia. " +
                "\nCảm ơn quý khách đã đồng hành với chúng tôi. " +
                "\nHotline: %s";

        public static final String CANCEL = "Upde xác nhận quý khách đã hủy thông công mã chuyến: %s." +
                "\nHOTLINE: %s";
    }
}
