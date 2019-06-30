package stp.cuonghq.upde.screen.changepassword;

import org.apache.commons.lang3.StringUtils;

import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.ApiCallback;
import stp.cuonghq.upde.commons.AppContext;
import stp.cuonghq.upde.commons.BasePresenter;
import stp.cuonghq.upde.data.repositories.AccountRepository;

public class Presenter extends BasePresenter<ChangePasswordActivity> implements Contract.Presenter{

    @Override
    public void changePassword(String oldPassword, String newPassword, String confirmPassword) {
        if (!StringUtils.equals(confirmPassword, newPassword)) {
            getView().passwordDoNotMatch(AppContext.getInstance().getResources().getString(R.string.error_password_do_not_match));
        } else {
            AccountRepository.getInstance().changePassword(oldPassword, newPassword, new ApiCallback() {
                @Override
                public void success(Object data, String msg) {
                    getView().changePasswordSuccess(msg);
                }

                @Override
                public void failed(String msg) {
                    getView().changePasswordFailed(msg);
                }
            });
        }
    }
}
