package stp.cuonghq.upde.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingChangeStatus {

    @Expose
    @SerializedName("id_host")
    private String idHost;

    public BookingChangeStatus(String idHost, String idTrip) {
        this.idHost = idHost;
        this.idTrip = idTrip;
    }

    @Expose
    @SerializedName("id_trip")


    private String idTrip;

    public String getIdHost() {
        return idHost;
    }

    public void setIdHost(String idHost) {
        this.idHost = idHost;
    }

    public String getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(String idTrip) {
        this.idTrip = idTrip;
    }
}
