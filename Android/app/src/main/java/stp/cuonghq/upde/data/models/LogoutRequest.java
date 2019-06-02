package stp.cuonghq.upde.data.models;

import com.google.gson.annotations.SerializedName;

public class LogoutRequest {

    @SerializedName("token_firebase")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
