package stp.cuonghq.upde.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cuong.hq1 on 6/10/2019.
 */

public class DataStatisticTime {

    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("number_trip")
    @Expose
    private Integer numberTrip;

    private Integer pagenumber;

    public DataStatisticTime(String time , Double price , Integer numberTrip){
        this.time = time;
        this.price = price;
        this.numberTrip = numberTrip;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNumberTrip() {
        return numberTrip;
    }

    public void setNumberTrip(Integer numberTrip) {
        this.numberTrip = numberTrip;
    }


}
