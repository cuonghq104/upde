package stp.cuonghq.upde.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cuong.hq1 on 6/5/2019.
 */

public class StatisticGetAllPriceResponce {

    @SerializedName("data")
    @Expose
    private List<DataStatisticTime> data = null;

    public List<DataStatisticTime> getData() {
        return data;
    }

    public void setData(List<DataStatisticTime> data) {
        this.data = data;
    }
}
