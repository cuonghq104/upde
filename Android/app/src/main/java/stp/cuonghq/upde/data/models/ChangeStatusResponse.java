package stp.cuonghq.upde.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeStatusResponse {

    @Expose
    @SerializedName("data")
    private ChangeStatusData data;

    public ChangeStatusData getResp() {
        return data;
    }

    public void setResp(ChangeStatusData data) {
        this.data = data;
    }
}
