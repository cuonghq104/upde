package stp.cuonghq.upde.screen.changepassword;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.AppToolbar;
import stp.cuonghq.upde.commons.BaseActivity;
import stp.cuonghq.upde.commons.Utilities;

public class ChangePasswordActivity extends BaseActivity<ChangePasswordActivity, Presenter> implements Contract.View {

    public static Intent getInstance(Context context) {
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        return intent;
    }

    @BindView(R.id.edt_password)
    AppCompatEditText mEdtPassword;
    @BindView(R.id.edt_new_password)
    AppCompatEditText mEdtNewPassword;
    @BindView(R.id.edt_confirm_password)
    AppCompatEditText mEdtConfirmPassword;
    @BindView(R.id.toolbar)
    AppToolbar mToolbar;

    @BindView(R.id.btn_hide_old_password)
    AppCompatImageButton mBtnHideOldPassword;
    @BindView(R.id.btn_hide_new_password)
    AppCompatImageButton mBtnHideNewPassword;
    @BindView(R.id.btn_hide_confirm)
    AppCompatImageButton mBtnHideConfirm;

    private AppCompatImageButton mBtnToolbarRight;
    private AppCompatImageButton mBtnBack;
    private AppCompatTextView mTvToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
        setupUI();
        addListener();
    }

    @Override
    protected Presenter initPresenter() {
        return new Presenter();
    }

    private void addListener() {
        mToolbar.setLeftBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordActivity.this.finish();
            }
        });

        mEdtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equalsIgnoreCase("")) {
                    mBtnHideOldPassword.setVisibility(View.GONE);
                } else {
                    mBtnHideOldPassword.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEdtConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equalsIgnoreCase("")) {
                    mBtnHideConfirm.setVisibility(View.GONE);
                } else {
                    mBtnHideConfirm.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEdtNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equalsIgnoreCase("")) {
                    mBtnHideNewPassword.setVisibility(View.GONE);
                } else {
                    mBtnHideNewPassword.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setupUI() {
        mToolbar.setTitle(getResources().getString(R.string.title_change_password));
        mBtnBack.setImageResource(R.drawable.ic_left_arrow);
        mBtnBack.setVisibility(View.VISIBLE);

        mBtnHideConfirm.setVisibility(View.GONE);
        mBtnHideNewPassword.setVisibility(View.GONE);
        mBtnHideOldPassword.setVisibility(View.GONE);
    }

    private void initView() {
        ButterKnife.bind(this);

        mBtnToolbarRight = mToolbar.findViewById(R.id.btn_right);
        mBtnBack = mToolbar.findViewById(R.id.btn_left);
        mTvToolbar = mToolbar.findViewById(R.id.tv_title);
    }

    private void changeInputType(AppCompatEditText mEdtText, AppCompatImageButton mBtnHide) {
        if (mEdtText.getTransformationMethod() instanceof PasswordTransformationMethod) {
            mEdtText.setTransformationMethod(new SingleLineTransformationMethod());
            mBtnHide.setImageResource(R.drawable.ic_visibility_off_black_24dp);
        } else {
            mEdtText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mBtnHide.setImageResource(R.drawable.ic_visibility_black_24dp);
        }
    }

    @OnClick(R.id.btn_hide_old_password)
    public void hideOldPassword() {
        changeInputType(mEdtPassword, mBtnHideOldPassword);
    }

    @OnClick(R.id.btn_hide_new_password)
    public void hidePassword() {
        changeInputType(mEdtNewPassword, mBtnHideNewPassword);
    }

    @OnClick(R.id.btn_hide_confirm)
    public void hideConfirmPassword() {
        changeInputType(mEdtConfirmPassword, mBtnHideConfirm);
    }

    @OnClick(R.id.btn_save)
    public void changePassword() {
        String oldPassword = mEdtPassword.getText().toString();
        String newPassword = mEdtNewPassword.getText().toString();
        String confirmPassword = mEdtConfirmPassword.getText().toString();

        if (StringUtils.isEmpty(oldPassword)) {
            mEdtPassword.setError(getResources().getString(R.string.error_can_not_be_empty));
            return;
        }

        if (StringUtils.isEmpty(newPassword)) {
            mEdtNewPassword.setError(getResources().getString(R.string.error_can_not_be_empty));
            return;
        }

        if (StringUtils.isEmpty(confirmPassword)) {
            mEdtConfirmPassword.setError(getResources().getString(R.string.error_can_not_be_empty));
            return;
        }

        presenter.changePassword(oldPassword, newPassword, confirmPassword);
    }
    @Override
    public void passwordDoNotMatch(String msg) {
        Utilities.showToast(getApplicationContext(), msg);
    }

    @Override
    public void changePasswordSuccess(String msg) {
        Utilities.showToast(getApplicationContext(), msg);
        ChangePasswordActivity.this.finish();
    }

    @Override
    public void changePasswordFailed(String msg) {
        Utilities.showToast(getApplicationContext(), msg);
    }
}
