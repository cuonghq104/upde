package com.example.upde_sms.services;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.Log;

import com.example.upde_sms.commons.Constants;
import com.example.upde_sms.commons.SmsReceiver;
import com.example.upde_sms.models.TripNotification;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Map;

public class UpdeFCM extends FirebaseMessagingService {

    private static final String TAG = UpdeFCM.class.toString();

    private static final String SENT_INTENT = "com.example.upde_sms.services.SENT";
    private static final String RECEIVE_INTENT = "com.example.upde_sms.services.RECEIVE";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {

            Map<String, String> data = remoteMessage.getData();
            String json = (String) data.values().toArray()[0];

            TripNotification notification = new Gson().fromJson(json, TripNotification.class);

            if (notification != null) {

                String phone = notification.getCustomerPhoneNumber();
                String smsContent = "";

                if (StringUtils.equals(Constants.SMSTemplete.BOOKING_TYPE, notification.getNotificationType())) {
                    smsContent = String.format(
                            Constants.SMSTemplete.BOOKING,
                            notification.getTimeBooking(),
                            notification.getTripId(),
                            notification.getTripDestination(),
                            notification.getNumsOfCustomer(),
                            notification.getHotline()
                    );
                } else if (StringUtils.equals(Constants.SMSTemplete.CONFIRM_TYPE, notification.getNotificationType())) {
                    smsContent = String.format(
                            Constants.SMSTemplete.CONFIRM,
                            notification.getTripId(),
                            notification.getDriverName(),
                            notification.getDriverPhoneNumber(),
                            notification.getDriverLicensePlate(),
                            notification.getDriverCarModel(),
                            notification.getHotline()
                    );
                } else if (StringUtils.equals(Constants.SMSTemplete.COMPLETE_TYPE, notification.getNotificationType())) {
                    smsContent = String.format(
                            Constants.SMSTemplete.COMPLETE,
                            notification.getTripId(),
                            notification.getHotline()
                    );
                } else {
                    smsContent = String.format(
                            Constants.SMSTemplete.CANCEL,
                            notification.getTripId(),
                            notification.getHotline()
                    );
                }

                Log.d(TAG, "Message: " + smsContent);
                PendingIntent sendPI = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(SENT_INTENT), 0);
                PendingIntent receivePI = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(RECEIVE_INTENT), 0);

                ArrayList<PendingIntent> sendPIList = new ArrayList<>();
                sendPIList.add(sendPI);

                ArrayList<PendingIntent> receivePIList = new ArrayList<>();
                receivePIList.add(receivePI);

                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendMultipartTextMessage(phone, null, smsManager.divideMessage(smsContent), sendPIList, receivePIList);

            }
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerReceiver(SmsReceiver.SentReceiver.getSmsSentReceiver(), new IntentFilter(SENT_INTENT));
        registerReceiver(SmsReceiver.ReceiveReceiver.getSmsReceiveReceiver(), new IntentFilter(RECEIVE_INTENT));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(SmsReceiver.SentReceiver.getSmsSentReceiver());
        unregisterReceiver(SmsReceiver.ReceiveReceiver.getSmsReceiveReceiver());
    }
}
