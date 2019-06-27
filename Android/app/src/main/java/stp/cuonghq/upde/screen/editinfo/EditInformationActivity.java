package stp.cuonghq.upde.screen.editinfo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import java.util.Calendar;

import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.BaseActivity;
import stp.cuonghq.upde.commons.Constants;
import stp.cuonghq.upde.commons.Utilities;
import stp.cuonghq.upde.data.models.LoginData;

public class EditInformationActivity extends BaseActivity<EditInformationActivity, Presenter> implements Contract.View{

    public static Intent getInstance(Context context) {
        Intent intent = new Intent(context, EditInformationActivity.class);
        return intent;
    }

    private View mToolbar;
    private AppCompatEditText mEdtName, mEdtPhone;
    private AppCompatTextView mTvPassword;
    private AppCompatButton mBtnEdit;

    private AppCompatTextView mTvToolbar;
    private AppCompatImageButton mBtnBack;
    private AppCompatImageButton mBtnToolbarRight;

    private LoginData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);
        initView();
        initData();
        setupUI();
        addListener();
    }

    private void setupUI() {
        mEdtName.setText((data == null) ? "" : data.getName());
    }

    @Override
    protected Presenter initPresenter() {
        return new Presenter();
    }

    private void addListener() {
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditInformationActivity.this.finish();
            }
        });

        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEdtName.getText().toString();
                String phone = mEdtPhone.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    mEdtName.setError("Can't be empty");
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    mEdtPhone.setError("Can't be empty");
                    return;
                }

                presenter.updateInformation(phone, name);
            }
        });

    }

    private final Calendar mCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener mListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mCalendar.set(Calendar.YEAR, year);
            mCalendar.set(Calendar.MONTH, month);
            mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        }
    };

    private void initData() {
        mBtnBack.setVisibility(View.VISIBLE);
        mBtnBack.setImageResource(R.drawable.ic_left_arrow);
        mTvToolbar.setText(getString(R.string.title_edit_information));

        data = presenter.getLoginData();
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mEdtName = findViewById(R.id.edt_name);
        mEdtPhone = findViewById(R.id.edt_phone);
        mTvPassword = findViewById(R.id.btn_change_password);
        mBtnEdit = findViewById(R.id.btn_save);

        mBtnToolbarRight = mToolbar.findViewById(R.id.btn_right);
        mBtnBack = mToolbar.findViewById(R.id.btn_left);
        mTvToolbar = mToolbar.findViewById(R.id.tv_title);
    }

    @Override
    public void doneUpdateInformation(boolean success, String msg) {
        Utilities.showToast(getApplicationContext(), msg);
        if (success) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra(Constants.Extras.RESULT, true);
            setResult(Activity.RESULT_OK, returnIntent);

            EditInformationActivity.this.finish();
        }
    }
}
