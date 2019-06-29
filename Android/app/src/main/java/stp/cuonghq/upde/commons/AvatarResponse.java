package stp.cuonghq.upde.commons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvatarResponse {

    @Expose
    @SerializedName("avatar")
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
