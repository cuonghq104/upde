package stp.cuonghq.upde.screen.start.signin;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.AppSharePreferences;
import stp.cuonghq.upde.commons.BaseFragment;
import stp.cuonghq.upde.commons.Constants;
import stp.cuonghq.upde.commons.Utilities;
import stp.cuonghq.upde.screen.container.SupplierContainerActivity;
import stp.cuonghq.upde.screen.container.HostContainerActivity;
import stp.cuonghq.upde.screen.start.StartActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends BaseFragment<SignInFragment, Presenter> implements Contract.View {

    @BindView(R.id.layout_loading)
    View mLoading;

    @BindView(R.id.btn_forget_password)
    AppCompatTextView mBtnForgetPassword;

    @BindView(R.id.btn_login)
    AppCompatButton mBtnLogin;

    @BindView(R.id.btn_login_as_host)
    AppCompatButton mBtnLoginAsHost;

    @BindView(R.id.edt_email)
    AppCompatEditText mEdtEmail;

    @BindView(R.id.edt_password)
    AppCompatEditText mEdtPassword;

    @BindView(R.id.til_email)
    TextInputLayout mEmailContainer;

    @BindView(R.id.til_password)
    TextInputLayout mPasswordContainer;

    @BindView(R.id.btn_clear)
    AppCompatImageButton mBtnClear;

    @BindView(R.id.btn_hide_password)
    AppCompatImageButton mBtnHide;

//    Presenter mPresenter;

    public static SignInFragment create() {
        return new SignInFragment();
    }

    public static SignInFragment create(String email, String password) {
        SignInFragment fragment = new SignInFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.Extras.EMAIL, email);
        bundle.putString(Constants.Extras.PASSWORD, password);
        fragment.setArguments(bundle);
        return fragment;
    }

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        initView(view);
        initData();
        addListener();
        return view;
    }

    @Override
    protected Presenter initPresenter() {
        Presenter mPresenter = new Presenter();
        mPresenter.setView(SignInFragment.this);
        return mPresenter;
    }

    private void initView(View view) {
        ButterKnife.bind(this, view);
        mEdtPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    private void initData() {
        String email = AppSharePreferences.getStringFromSP(Constants.SharePreferenceConstants.EMAIL);
        String password = AppSharePreferences.getStringFromSP(Constants.SharePreferenceConstants.PASSWORD);
        Log.d("Login: share", email +" "+ password);
        if (!TextUtils.equals(email, "") && !TextUtils.equals(password, "")) {
            mEdtEmail.setText(email);
            mEdtPassword.setText(password);
        }
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//
//            String email = bundle.getString("email");
//            String password = bundle.getString("password");
//
//            if (!TextUtils.equals(email, "") && !TextUtils.equals(password, "")) {
//
//                mEdtEmail.setText(email);
//                mEdtPassword.setText(password);
//            }
//        }
    }

    private void addListener() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LOGIN_AS_TYPE", Constants.LOGIN_AS_SUPPLIER_TYPE);
                presenter.login(mEdtEmail.getText().toString().toLowerCase(), mEdtPassword.getText().toString(), Constants.LOGIN_AS_SUPPLIER_TYPE);
            }
        });

        mBtnLoginAsHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LOGIN_AS_TYPE", Constants.LOGIN_AS_HOST_TYPE);
                presenter.login(mEdtEmail.getText().toString().toLowerCase(), mEdtPassword.getText().toString(), Constants.LOGIN_AS_HOST_TYPE);
            }
        });

        mBtnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.showToast(getContext(), "This feature is not supported in this version");
            }
        });

        mEdtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equalsIgnoreCase("")) {
                    mBtnHide.setVisibility(View.GONE);
                } else {
                    mBtnHide.setVisibility(View.VISIBLE);
                }
                presenter.passwordChange(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                mPasswordContainer.setErrorEnabled(false);
            }
        });

        mEdtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBtnClear.setVisibility((s.toString().equals("")) ? View.GONE : View.VISIBLE);
                presenter.emailChange(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                mEmailContainer.setErrorEnabled(false);
            }
        });
    }

    @Override
    public void doLogin() {
        mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void loginSuccess(String msg, String type) {
        mLoading.setVisibility(View.GONE);
        Log.d("Login Success", msg);
        Activity mActivity = getActivity();

        // Start activity responding here
        if (mActivity instanceof StartActivity) {
            Intent intent;
            if(type.equals(Constants.LOGIN_AS_SUPPLIER_TYPE)){
                intent = new Intent(this.getActivity().getApplicationContext(), SupplierContainerActivity.class);
            } else {
                intent = new Intent(this.getActivity().getApplicationContext(), HostContainerActivity.class);
            }
            startActivity(intent);
            mActivity.finish();
        }
    }

    @Override
    public void loginFailed(String msg) {
        mLoading.setVisibility(View.GONE);
        Utilities.showToast(getContext(), msg);
    }

    @Override
    public void updateEmail(String email) {
        mEdtEmail.setText(email);
    }

    @Override
    public void updatePassword(String password) {
        mEdtPassword.setText(password);
    }

    @OnClick(R.id.btn_clear)
    public void clearEmail() {
        mEdtEmail.setText("");
    }

    @OnClick(R.id.btn_hide_password)
    public void hidePassword() {

        if (mEdtPassword.getTransformationMethod() instanceof PasswordTransformationMethod) {
            mEdtPassword.setTransformationMethod(new SingleLineTransformationMethod());
            mBtnHide.setImageResource(R.drawable.ic_visibility_off_black_24dp);
        } else {
            mEdtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mBtnHide.setImageResource(R.drawable.ic_visibility_black_24dp);
        }
    }
}
