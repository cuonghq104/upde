package stp.cuonghq.upde.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingList {

    @SerializedName("trips")
    private List<BookingResp> list;

    public List<BookingResp> getList() {
        return list;
    }

    public void setList(List<BookingResp> list) {
        this.list = list;
    }
}
