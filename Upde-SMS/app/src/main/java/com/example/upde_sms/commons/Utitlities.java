package com.example.upde_sms.commons;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class Utitlities {

    private static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void checkPermission(CommonCallbacks.RequestPermissionCallback callback, Context mContext, String ... permissionNeeded) {
        if (!hasPermissions(mContext, permissionNeeded)) {
            callback.permissionDenied();
        } else {
            callback.permissionGranted();
        }
    }
}
