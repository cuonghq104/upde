package com.example.upde_sms.commons;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

public class CommonCallbacks {

    public static final String TAG = CommonCallbacks.class.toString();

    public interface RequestPermissionCallback {
        void permissionGranted();

        void permissionDenied();
    }


}
