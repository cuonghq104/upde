package stp.cuonghq.upde.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BookingResp implements Serializable {

    @Expose
    @SerializedName("note")
    private String note;
    @Expose
    @SerializedName("TimeLeave")
    private String timeleave;
    @Expose
    @SerializedName("Id_trip")
    private String idTrip;
    @Expose
    @SerializedName("NameArrive")
    private String nameArrive;
    @Expose
    @SerializedName("HomeName")
    private String nameHome;
    @Expose
    @SerializedName("Price_vn")
    private int priceVn;
    @Expose
    @SerializedName("Price")
    private double price;
    @Expose
    @SerializedName("Vehicle_type")
    private String vehicleType;
    @Expose
    @SerializedName("PhoneNumber")
    private String phonenumber;
    @Expose
    @SerializedName("EmailGuest")
    private String emailguest;
    @Expose
    @SerializedName("Name_customer")
    private String nameCustomer;
    @Expose
    @SerializedName("TypeTrip")
    private int typetrip;
    @Expose
    @SerializedName("Serial")
    private int serial;
    @Expose
    @SerializedName("FlightCode")
    private String flightNo;

    private boolean read = true;

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTimeleave() {
        return timeleave;
    }

    public void setTimeleave(String timeleave) {
        this.timeleave = timeleave;
    }

    public String getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(String idTrip) {
        this.idTrip = idTrip;
    }

    public String getNameArrive() {
        return nameArrive;
    }

    public void setNameArrive(String nameArrive) {
        this.nameArrive = nameArrive;
    }

    public String getNameHome() {
        return nameHome;
    }

    public void setNameHome(String nameHome) {
        this.nameHome = nameHome;
    }

    public int getPriceVn() {
        return priceVn;
    }

    public void setPriceVn(int priceVn) {
        this.priceVn = priceVn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmailguest() {
        return emailguest;
    }

    public void setEmailguest(String emailguest) {
        this.emailguest = emailguest;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public int getTypetrip() {
        return typetrip;
    }

    public void setTypetrip(int typetrip) {
        this.typetrip = typetrip;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }
}
