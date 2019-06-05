package stp.cuonghq.upde.screen.editinfo;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import java.util.Calendar;

import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.NonPresenterActivity;
import stp.cuonghq.upde.commons.Utilities;

public class EditInformationActivity extends NonPresenterActivity {

    public static Intent getInstance(Context context) {
        Intent intent = new Intent(context, EditInformationActivity.class);
        return intent;
    }

    private View mToolbar;
    private AppCompatEditText mEdtName, mEdtEmail, mEdtDateOfBirth;
    private AppCompatTextView mTvPassword;
    private AppCompatSpinner mSpnGender;

    private String[] genders = {"Male", "Female"};
    private ArrayAdapter<String> spnAdapter;

    private AppCompatTextView mTvToolbar;
    private AppCompatImageButton mBtnBack;
    private AppCompatImageButton mBtnToolbarRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);
        initView();
        initData();
        addListener();
    }

    private void addListener() {
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditInformationActivity.this.finish();
            }
        });

        mEdtDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDialog = new DatePickerDialog(EditInformationActivity.this, mListener, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
                mDialog.setTitle(getString(R.string.title_pick));
                mDialog.show();
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

            mEdtDateOfBirth.setText(Utilities.dateToString(mCalendar.getTime()));
        }
    };

    private void initData() {
        spnAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, genders);
        mSpnGender.setAdapter(spnAdapter);
        mBtnBack.setVisibility(View.VISIBLE);
        mBtnBack.setImageResource(R.drawable.ic_left_arrow);
        mTvToolbar.setText(getString(R.string.title_edit_information));
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mEdtName = findViewById(R.id.edt_name);
        mEdtEmail = findViewById(R.id.edt_email);
        mEdtDateOfBirth = findViewById(R.id.edt_date_of_birth);
        mTvPassword = findViewById(R.id.btn_change_password);
        mSpnGender = findViewById(R.id.spn_gender);

        mBtnToolbarRight = mToolbar.findViewById(R.id.btn_right);
        mBtnBack = mToolbar.findViewById(R.id.btn_left);
        mTvToolbar = mToolbar.findViewById(R.id.tv_title);
    }
}
