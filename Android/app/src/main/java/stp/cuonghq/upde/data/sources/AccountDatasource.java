package stp.cuonghq.upde.data.sources;

import stp.cuonghq.upde.commons.ApiCallback;
import stp.cuonghq.upde.data.models.LoginData;


public class AccountDatasource {

    public interface RDS {
        void login(String email, String password, String token, ApiCallback<LoginData> callback);

        void loginAsHost(String email, String password, ApiCallback<LoginData> callback);

        void logOut(String token, ApiCallback callback);

        void checkTokenStatus(ApiCallback callback);

        void editInformation(String phone, String name, ApiCallback callback);

        void changeProfileImage(String fileType, String filePath, ApiCallback callback);
    }
}
