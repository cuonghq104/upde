package stp.cuonghq.upde.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cuong.hq1 on 6/5/2019.
 */

public class StatisticGetAllPriceRequest {

    @SerializedName("type_get")
    @Expose
    private String typeGet;
    @SerializedName("number")
    @Expose
    private int number;

    public StatisticGetAllPriceRequest(String type , int number){
        this.typeGet = type;
        this.number = number;
    }

    public String getTypeGet() {
        return typeGet;
    }

    public void setTypeGet(String typeGet) {
        this.typeGet = typeGet;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

}
