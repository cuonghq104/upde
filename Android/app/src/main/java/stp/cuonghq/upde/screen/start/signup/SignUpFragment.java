package stp.cuonghq.upde.screen.start.signup;


import android.app.Activity;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import stp.cuonghq.upde.R;
import stp.cuonghq.upde.screen.start.StartActivity;
import stp.cuonghq.upde.screen.start.signin.SignInFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    public static SignUpFragment create() {
        return new SignUpFragment();
    }

    @BindView(R.id.layout_loading)
    View mLoading;

    @BindView(R.id.btn_login)
    AppCompatTextView mBtnSignIn;

    @BindView(R.id.edt_name)
    AppCompatEditText mEdtName;

    @BindView(R.id.edt_phone)
    AppCompatEditText mEdtEmail;

    @BindView(R.id.edt_password)
    AppCompatEditText mEdtPassword;

    @BindView(R.id.edt_confirm_password)
    AppCompatEditText mEdtConfirm;

    @BindView(R.id.til_name)
    TextInputLayout mEdtContainerName;

    @BindView(R.id.til_email)
    TextInputLayout mEdtContainerEmail;

    @BindView(R.id.til_password)
    TextInputLayout mEdtContainerPassword;

    @BindView(R.id.til_confirm_password)
    TextInputLayout mEdtContainerConfirm;

    @BindView(R.id.btn_sign_up)
    AppCompatButton mBtnSignUp;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initView(view);
        initData();
        addListener();
        return view;
    }

    private void addListener() {
        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mPresenter.signUp(
//                        mEdtName.getText().toString(),
//                        mEdtEmail.getText().toString(),
//                        mEdtPassword.getText().toString(),
//                        mEdtConfirm.getText().toString()
//                );
            }
        });

        mBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Activity mActivity = getActivity();
                if (mActivity instanceof StartActivity) {
                    ((StartActivity) mActivity).changeFragment(SignInFragment.create());
                }
            }
        });

        mEdtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mEdtContainerName.setErrorEnabled(false);
            }
        });

        mEdtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mEdtContainerEmail.setErrorEnabled(false);
            }
        });

        mEdtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mEdtContainerPassword.setErrorEnabled(false);
            }
        });

        mEdtConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mEdtContainerConfirm.setErrorEnabled(false);
            }
        });
    }

    private void initData() {

    }

    private void initView(View view) {
        ButterKnife.bind(this, view);
    }

}
