package stp.cuonghq.upde.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cuong.hq1 on 6/5/2019.
 */

public class StatisticTripCompleteRequest {

    @SerializedName("time_begin")
    @Expose
    private String time_begin;
    @SerializedName("time_end")
    @Expose
    private String time_end;
    @SerializedName("serial_before")
    @Expose
    private long serial_before;

    public StatisticTripCompleteRequest(String time_begin , String time_end , long number){
        this.time_begin = time_begin;
        this.time_end = time_end;
        this.serial_before = number;
    }

    public String getTime_begin() {
        return time_begin;
    }

    public void setTime_begin(String time_begin) {
        this.time_begin = time_begin;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public long getSerial_before() {
        return serial_before;
    }

    public void setSerial_before(long serial_before) {
        this.serial_before = serial_before;
    }
}
