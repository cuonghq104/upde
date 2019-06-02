package stp.cuonghq.upde.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ChangeStatusData implements Serializable {
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("note")
    private String note;
    @Expose
    @SerializedName("type")
    private int type;
    @Expose
    @SerializedName("id_host")
    private String idHost;
    @Expose
    @SerializedName("time_book")
    private String timeBook;
    @Expose
    @SerializedName("time_leave")
    private String timeLeave;
    @Expose
    @SerializedName("name_arrive")
    private String nameArrive;
    @Expose
    @SerializedName("name_leave")
    private String nameLeave;
    @Expose
    @SerializedName("price_vn")
    private int priceVn;
    @Expose
    @SerializedName("price")
    private double price;
    @Expose
    @SerializedName("vehicle_type")
    private String vehicleType;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("phone_number")
    private String phoneNumber;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("name_passenger")
    private String namePassenger;
    @Expose
    @SerializedName("serial")
    private int serial;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIdHost() {
        return idHost;
    }

    public void setIdHost(String idHost) {
        this.idHost = idHost;
    }

    public String getTimeBook() {
        return timeBook;
    }

    public void setTimeBook(String timeBook) {
        this.timeBook = timeBook;
    }

    public String getTimeLeave() {
        return timeLeave;
    }

    public void setTimeLeave(String timeLeave) {
        this.timeLeave = timeLeave;
    }

    public String getNameArrive() {
        return nameArrive;
    }

    public void setNameArrive(String nameArrive) {
        this.nameArrive = nameArrive;
    }

    public String getNameLeave() {
        return nameLeave;
    }

    public void setNameLeave(String nameLeave) {
        this.nameLeave = nameLeave;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNamePassenger() {
        return namePassenger;
    }

    public void setNamePassenger(String namePassenger) {
        this.namePassenger = namePassenger;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }
}
