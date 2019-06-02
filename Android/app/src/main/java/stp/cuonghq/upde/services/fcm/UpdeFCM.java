package stp.cuonghq.upde.services.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.Constants;
import stp.cuonghq.upde.commons.Utilities;
import stp.cuonghq.upde.screen.bookingdetail.BookingDetailActivity;

import static stp.cuonghq.upde.commons.Constants.CHANNEL_DISCRIPTION;
import static stp.cuonghq.upde.commons.Constants.CHANNEL_ID;
import static stp.cuonghq.upde.commons.Constants.CHANNEL_NAME;

public class UpdeFCM extends FirebaseMessagingService {

    private static final String TAG = UpdeFCM.class.toString();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Map<String, String> data = remoteMessage.getData();
            String json = (String) data.values().toArray()[0];

            SharedPreferences sp = Utilities.getSP(getApplicationContext());
            boolean active = sp.getBoolean(Constants.SharePreferenceConstants.STATE, false);
            if (!active) {
                showNotification(json);
            } else {
                Intent intent = new Intent();
                intent.setAction("service.to.activity.transfer");
                intent.putExtra(Constants.Extras.BOOKING, json);
                getApplication().getApplicationContext().sendBroadcast(intent);
                Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            }
        }
    }

    private void showNotification(String json) {
        Booking booking = new Gson().fromJson(json, Booking.class);

        if (booking.getType().equalsIgnoreCase("notify_book")) {
            Intent intent = BookingDetailActivity.getInstance(getApplicationContext(), booking);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, Constants.PENDING_INTENT_FCM_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification_layout);

            int imgRes;
            String totalSeats;

            if ("sedan".equalsIgnoreCase(booking.getVehicleType())) {
                imgRes = R.drawable.group_2;
                totalSeats = 5 + getString(R.string.title_seat);
            } else if ("suv".equalsIgnoreCase(booking.getVehicleType())) {
                imgRes = R.drawable.suv;
                totalSeats = 7 + getString(R.string.title_seat);
            } else {
                imgRes = R.drawable.minivan;
                totalSeats = 16 + getString(R.string.title_seat);
            }

            notificationLayout.setTextViewText(R.id.tv_type, totalSeats);
            notificationLayout.setImageViewResource(R.id.imv_type, imgRes);
            notificationLayout.setTextViewText(R.id.tv_time, booking.getTimeleave());
            notificationLayout.setTextViewText(R.id.tv_price, Utilities.convertToVnd(booking.getPriceVn()));

            String title1 = " đã đặt một chuyến xe từ ";
            String title2 = " đến ";
            String title3 = ". Ghi chú: ";

            String email = booking.getEmailguest();
            String pickup = booking.getNameleave();
            String destination = booking.getNamearrive();
            String note = booking.getNote();

            notificationLayout.setTextViewText(R.id.tv_content, email + title1 + pickup + title2 + destination + title3 + note);

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                        CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription(CHANNEL_DISCRIPTION);
                manager.createNotificationChannel(channel);
            }

            try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Notification notification = new NotificationCompat.Builder(getBaseContext(), CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .setCustomContentView(notificationLayout)
                    .setContentIntent(pendingIntent)
                    .build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;

            manager.notify(Constants.NOTIFICATION_NEW_BOOKING, notification);
        }
    }

    public static class Booking {

        @Expose
        @SerializedName("TimeComplete")
        private String timecomplete;
        @Expose
        @SerializedName("TimeLeave")
        private String timeleave;
        @Expose
        @SerializedName("note")
        private String note;
        @Expose
        @SerializedName("IsCancel")
        private boolean iscancel;
        @Expose
        @SerializedName("IdHost")
        private String idhost;
        @Expose
        @SerializedName("EmailHost")
        private String emailhost;
        @Expose
        @SerializedName("TimePickNow")
        private String timepicknow;
        @Expose
        @SerializedName("HostName")
        private String hostname;
        @Expose
        @SerializedName("Id_schedule")
        private String idSchedule;
        @Expose
        @SerializedName("Id_home")
        private String idHome;
        @Expose
        @SerializedName("PhoneNumber")
        private String phonenumber;
        @Expose
        @SerializedName("TypeTrip")
        private String typetrip;
        @Expose
        @SerializedName("NameArrive")
        private String namearrive;
        @Expose
        @SerializedName("EmailGuest")
        private String emailguest;
        @Expose
        @SerializedName("Id_trip")
        private String idTrip;
        @Expose
        @SerializedName("NameLeave")
        private String nameleave;
        @Expose
        @SerializedName("Price_vn")
        private int priceVn;
        @Expose
        @SerializedName("Price")
        private String price;
        @Expose
        @SerializedName("Time_book")
        private String timeBook;
        @Expose
        @SerializedName("Vehicle_type")
        private String vehicleType;
        @Expose
        @SerializedName("Arrive")
        private String arrive;
        @Expose
        @SerializedName("Name_customer")
        private String nameCustomer;
        @Expose
        @SerializedName("Serial")
        private int serial;
        @Expose
        @SerializedName("Type")
        private String type;
        @Expose
        @SerializedName("FlightCode")
        private String flightNo;

        public boolean isIscancel() {
            return iscancel;
        }

        public String getFlightNo() {
            return flightNo;
        }

        public void setFlightNo(String flightNo) {
            this.flightNo = flightNo;
        }

        public String getTimecomplete() {
            return timecomplete;
        }

        public void setTimecomplete(String timecomplete) {
            this.timecomplete = timecomplete;
        }

        public String getTimeleave() {
            return timeleave;
        }

        public void setTimeleave(String timeleave) {
            this.timeleave = timeleave;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public boolean getIscancel() {
            return iscancel;
        }

        public void setIscancel(boolean iscancel) {
            this.iscancel = iscancel;
        }

        public String getIdhost() {
            return idhost;
        }

        public void setIdhost(String idhost) {
            this.idhost = idhost;
        }

        public String getEmailhost() {
            return emailhost;
        }

        public void setEmailhost(String emailhost) {
            this.emailhost = emailhost;
        }

        public String getTimepicknow() {
            return timepicknow;
        }

        public void setTimepicknow(String timepicknow) {
            this.timepicknow = timepicknow;
        }

        public String getHostname() {
            return hostname;
        }

        public void setHostname(String hostname) {
            this.hostname = hostname;
        }

        public String getIdSchedule() {
            return idSchedule;
        }

        public void setIdSchedule(String idSchedule) {
            this.idSchedule = idSchedule;
        }

        public String getIdHome() {
            return idHome;
        }

        public void setIdHome(String idHome) {
            this.idHome = idHome;
        }

        public String getPhonenumber() {
            return phonenumber;
        }

        public void setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }

        public String getTypetrip() {
            return typetrip;
        }

        public void setTypetrip(String typetrip) {
            this.typetrip = typetrip;
        }

        public String getNamearrive() {
            return namearrive;
        }

        public void setNamearrive(String namearrive) {
            this.namearrive = namearrive;
        }

        public String getEmailguest() {
            return emailguest;
        }

        public void setEmailguest(String emailguest) {
            this.emailguest = emailguest;
        }

        public String getIdTrip() {
            return idTrip;
        }

        public void setIdTrip(String idTrip) {
            this.idTrip = idTrip;
        }

        public String getNameleave() {
            return nameleave;
        }

        public void setNameleave(String nameleave) {
            this.nameleave = nameleave;
        }

        public int getPriceVn() {
            return priceVn;
        }

        public void setPriceVn(int priceVn) {
            this.priceVn = priceVn;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTimeBook() {
            return timeBook;
        }

        public void setTimeBook(String timeBook) {
            this.timeBook = timeBook;
        }

        public String getVehicleType() {
            return vehicleType;
        }

        public void setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
        }

        public String getArrive() {
            return arrive;
        }

        public void setArrive(String arrive) {
            this.arrive = arrive;
        }

        public String getNameCustomer() {
            return nameCustomer;
        }

        public void setNameCustomer(String nameCustomer) {
            this.nameCustomer = nameCustomer;
        }

        public int getSerial() {
            return serial;
        }

        public void setSerial(int serial) {
            this.serial = serial;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
