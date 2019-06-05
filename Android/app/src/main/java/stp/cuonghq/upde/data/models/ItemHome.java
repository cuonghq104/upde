package stp.cuonghq.upde.data.models;

/**
 * Created by bau.nv on 6/5/2019.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemHome {

//    @SerializedName("link")
//    @Expose
//    private String link;
//    @SerializedName("address")
//    @Expose
//    private String address;
//
//    public String getLink() {
//        return link;
//    }
//
//    public void setLink(String link) {
//        this.link = link;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }

    private String address;
    private String link;
    public ItemHome (String address, String link){
        this.address = address;
        this.link = link;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}