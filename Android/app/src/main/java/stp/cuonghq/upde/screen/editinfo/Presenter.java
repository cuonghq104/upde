package stp.cuonghq.upde.screen.editinfo;

import stp.cuonghq.upde.commons.ApiCallback;
import stp.cuonghq.upde.commons.AppSharePreferences;
import stp.cuonghq.upde.commons.BasePresenter;
import stp.cuonghq.upde.commons.Constants;
import stp.cuonghq.upde.data.models.LoginData;
import stp.cuonghq.upde.data.repositories.AccountRepository;

public class Presenter extends BasePresenter<EditInformationActivity> implements Contract.Presenter {

    @Override
    public LoginData getLoginData() {
        return AppSharePreferences.getFromSP(Constants.SharePreferenceConstants.DATA, LoginData.class);
    }

    @Override
    public void updateInformation(String phoneNumber, final String name) {
        AccountRepository.getInstance().editInfo(phoneNumber, name, new ApiCallback() {
            @Override
            public void success(Object data, String msg) {
                getView().doneUpdateInformation(true, msg);
                LoginData loginData = AppSharePreferences.getFromSP(Constants.SharePreferenceConstants.DATA, LoginData.class);
                loginData.setName(name);
                // loginData.setPhone(phone)
                AppSharePreferences.saveToSP(Constants.SharePreferenceConstants.DATA, loginData);
            }

            @Override
            public void failed(String msg) {
                getView().doneUpdateInformation(false, msg);
            }
        });
    }
}
