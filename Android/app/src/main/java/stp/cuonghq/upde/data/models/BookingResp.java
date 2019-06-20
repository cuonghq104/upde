package stp.cuonghq.upde.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BookingResp implements Serializable {

    @SerializedName("serial")
    @Expose
    private Integer serial;
    @SerializedName("name_customer")
    @Expose
    private String nameCustomer;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("vehicle_type")
    @Expose
    private String vehicleType;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("price_vn")
    @Expose
    private Integer priceVn;
    @SerializedName("name_leave")
    @Expose
    private String nameLeave;
    @SerializedName("name_arrive")
    @Expose
    private String nameArrive;
    @SerializedName("time_leave")
    @Expose
    private String timeLeave;
    @SerializedName("time_book")
    @Expose
    private String timeBook;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("geopoint")
    @Expose
    private Geopoint geopoint;
    @SerializedName("flight_code")
    @Expose
    private String flightCode;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("id")
    @Expose
    private String id;

    private boolean read = true;

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getFlightNo() {
        return flightCode;
    }

    public void setFlightNo(String flightNo) {
        this.flightCode = flightNo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTimeleave() {
        return timeLeave;
    }

    public void setTimeleave(String timeleave) {
        this.timeLeave = timeleave;
    }

    public String getIdTrip() {
        return id;
    }

    public void setIdTrip(String idTrip) {
        this.id = idTrip;
    }

    public String getNameArrive() {
        return nameArrive;
    }

    public void setNameArrive(String nameArrive) {
        this.nameArrive = nameArrive;
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
        return phoneNumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phoneNumber = phonenumber;
    }

    public String getEmailguest() {
        return email;
    }

    public void setEmailguest(String emailguest) {
        this.email = emailguest;
    }

    public int getTypetrip() {
        return type;
    }

    public void setTypetrip(int typetrip) {
        this.type = typetrip;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setPriceVn(Integer priceVn) {
        this.priceVn = priceVn;
    }

    public String getNameLeave() {
        return nameLeave;
    }

    public void setNameLeave(String nameLeave) {
        this.nameLeave = nameLeave;
    }

    public String getTimeLeave() {
        return timeLeave;
    }

    public void setTimeLeave(String timeLeave) {
        this.timeLeave = timeLeave;
    }

    public String getTimeBook() {
        return timeBook;
    }

    public void setTimeBook(String timeBook) {
        this.timeBook = timeBook;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Geopoint getGeopoint() {
        return geopoint;
    }

    public void setGeopoint(Geopoint geopoint) {
        this.geopoint = geopoint;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
