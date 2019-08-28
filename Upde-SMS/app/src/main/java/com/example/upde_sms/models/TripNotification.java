package com.example.upde_sms.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TripNotification implements Serializable {

    @SerializedName("customer_phone_number")
    private String customerPhoneNumber;

    @SerializedName("notification_type")
    private String notificationType;

    @SerializedName("trip_id")
    private String tripId;

    @SerializedName("trip_destination")
    private String tripDestination;

    @SerializedName("time_booking")
    private String timeBooking;

    @SerializedName("nums_of_customer")
    private String numsOfCustomer;

    @SerializedName("hotline")
    private String hotline;

    @SerializedName("driver_name")
    private String driverName;

    @SerializedName("driver_phone_number")
    private String driverPhoneNumber;

    @SerializedName("driver_license_plate")
    private String driverLicensePlate;

    @SerializedName("driver_car_model")
    private String driverCarModel;

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getTripDestination() {
        return tripDestination;
    }

    public void setTripDestination(String tripDestination) {
        this.tripDestination = tripDestination;
    }

    public String getTimeBooking() {
        return timeBooking;
    }

    public void setTimeBooking(String timeBooking) {
        this.timeBooking = timeBooking;
    }

    public String getHotline() {
        return hotline;
    }

    public void setHotline(String hotline) {
        this.hotline = hotline;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhoneNumber() {
        return driverPhoneNumber;
    }

    public void setDriverPhoneNumber(String driverPhoneNumber) {
        this.driverPhoneNumber = driverPhoneNumber;
    }

    public String getDriverLicensePlate() {
        return driverLicensePlate;
    }

    public void setDriverLicensePlate(String driverLicensePlate) {
        this.driverLicensePlate = driverLicensePlate;
    }

    public String getDriverCarModel() {
        return driverCarModel;
    }

    public void setDriverCarModel(String driverCarModel) {
        this.driverCarModel = driverCarModel;
    }

    public String getNumsOfCustomer() {
        return numsOfCustomer;
    }

    public void setNumsOfCustomer(String numsOfCustomer) {
        this.numsOfCustomer = numsOfCustomer;
    }
}
