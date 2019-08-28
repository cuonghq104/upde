package com.example.upde_sms.commons;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

public class SmsReceiver {

    public static final String TAG = SmsReceiver.class.toString();

    public static class SentReceiver {
        private static volatile BroadcastReceiver sSmsSentReceiver;

        public static BroadcastReceiver getSmsSentReceiver() {
            if (sSmsSentReceiver == null) {
                synchronized (SentReceiver.class) {
                    sSmsSentReceiver = new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            switch (getResultCode()) {
                                case Activity.RESULT_OK:
                                    Log.d(TAG, "sSmsSentReceiver: SMS Sent");
                                    break;
                                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                                    Log.d(TAG, "sSmsSentReceiver: Generic Failure");
                                    break;
                                case SmsManager.RESULT_ERROR_NO_SERVICE:
                                    Log.d(TAG, "sSmsSentReceiver: No Service");
                                    break;
                                case SmsManager.RESULT_ERROR_NULL_PDU:
                                    Log.d(TAG, "sSmsSentReceiver: Null PDU");
                                    break;
                                case SmsManager.RESULT_ERROR_RADIO_OFF:
                                    Log.d(TAG, "sSmsSentReceiver: Radio off");
                                    break;
                            }
                        }
                    };
                }
            }
            return sSmsSentReceiver;
        }
    }

    public static class ReceiveReceiver {
        private static volatile BroadcastReceiver sSmsReceiveReceiver;

        public static BroadcastReceiver getSmsReceiveReceiver() {
            if (sSmsReceiveReceiver == null) {
                synchronized (ReceiveReceiver.class) {
                    sSmsReceiveReceiver = new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            switch (getResultCode()) {
                                case Activity.RESULT_OK:
                                    Log.d(TAG, "sSmsReceiveReceiver: SMS delivered");
                                    break;
                                case Activity.RESULT_CANCELED:
                                    Log.d(TAG, "sSmsReceiveReceiver: SMS not delivered");
                                    break;
                            }
                        }
                    };
                }
            }
            return sSmsReceiveReceiver;
        }
    }

}
