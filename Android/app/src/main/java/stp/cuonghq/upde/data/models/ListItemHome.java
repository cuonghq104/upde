package stp.cuonghq.upde.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by bau.nv on 6/6/2019.
 */

public class ListItemHome {
    @SerializedName("data")
    private List<ItemHome> list;

    public List<ItemHome> getList() {
        return list;
    }

    public void setList(List<ItemHome> list) {
        this.list = list;
    }
}
