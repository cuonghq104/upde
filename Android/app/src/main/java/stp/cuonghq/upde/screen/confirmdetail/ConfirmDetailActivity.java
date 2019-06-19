package stp.cuonghq.upde.screen.confirmdetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.Constants;
import stp.cuonghq.upde.commons.DisplayTextView;
import stp.cuonghq.upde.commons.Utilities;
import stp.cuonghq.upde.data.models.BookingResp;
import stp.cuonghq.upde.data.models.ChangeStatusData;

public class ConfirmDetailActivity extends AppCompatActivity implements Contract.View {

    public static Intent getInstance(Context context, BookingResp booking, int operation) {
        Intent intent = new Intent(context, ConfirmDetailActivity.class);
        intent.putExtra(Constants.Extras.BOOKING, booking);
        intent.putExtra(Constants.Extras.OPERATION, operation);
        return intent;
    }

    public static Intent getInstance(Context context, BookingResp booking) {
        return getInstance(context, booking, Constants.Extras.CONFIRM);
    }

    @BindView(R.id.layout_loading)
    View mLoading;
    @BindView(R.id.tv_ticket_no)
    DisplayTextView mTvTicketNo;
    @BindView(R.id.tv_flight_no)
    DisplayTextView mTvFlightNo;
    @BindView(R.id.tv_name)
    DisplayTextView mTvName;
    @BindView(R.id.tv_pickup_address)
    DisplayTextView mTvPickupAddress;
    @BindView(R.id.tv_destination)
    DisplayTextView mTvDestination;
    @BindView(R.id.tv_vehicle_type)
    DisplayTextView mTvType;
    @BindView(R.id.tv_time_pick_up)
    DisplayTextView mTvPickupTime;
    @BindView(R.id.tv_price)
    DisplayTextView mTvPrice;
    @BindView(R.id.tv_email)
    DisplayTextView mTvEmail;
    @BindView(R.id.tv_phone)
    DisplayTextView mTvPhone;
    @BindView(R.id.btn_complete)
    AppCompatButton mBtnComplete;
    @BindView(R.id.tv_complete)
    AppCompatTextView mTvComplete;


    @BindView(R.id.btn_back)
    AppCompatImageButton mBtnBack;

    BookingResp booking;
    int operation;
    Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_detail);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = new Presenter();
        mPresenter.setView(ConfirmDetailActivity.this);
        booking = (BookingResp) getIntent().getSerializableExtra(Constants.Extras.BOOKING);
        operation = getIntent().getIntExtra(Constants.Extras.OPERATION, 0);
        if (booking != null) {
            setupUI();
        }

    }

    private void setupUI() {
        if (TextUtils.equals(booking.getFlightNo(), null)) {
            mTvFlightNo.setVisibility(View.GONE);
        } else {
            mTvFlightNo.setVisibility(View.VISIBLE);
            mTvFlightNo.setContent(booking.getFlightNo());
        }
        mTvTicketNo.setContent(booking.getSerial() + "");
        mTvName.setContent(booking.getNameCustomer());
        mTvPickupAddress.setContent(booking.getNameArrive());
        mTvDestination.setContent(booking.getNameHome());
        mTvType.setContent(StringUtils.capitalize(booking.getVehicleType()));
        mTvPickupTime.setContent(booking.getTimeleave());
        mTvPrice.setContent(Utilities.convertToVnd(booking.getPriceVn()));
        mTvEmail.setContent(booking.getEmailguest());
        mTvPhone.setVisibility((booking.getPhonenumber() == null || booking.getPhonenumber().equals("")) ? View.GONE : View.VISIBLE);
        mTvPhone.setContent(booking.getPhonenumber());

        if (operation == Constants.Extras.COMPLETE) {
            mBtnComplete.setVisibility(View.GONE);
            mTvComplete.setVisibility(View.VISIBLE);
            mBtnComplete.setOnClickListener(null);
        } else if (operation == Constants.Extras.CONFIRM) {
            mBtnComplete.setVisibility(View.VISIBLE);
            mTvComplete.setVisibility(View.GONE);
        }
    }

    private void initView() {
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_complete)
    public void completeBooking() {
        if (mPresenter != null) {
            mPresenter.completeBooking(booking.getIdTrip());
        }
    }

    @OnClick(R.id.btn_back)
    public void back() {
        ConfirmDetailActivity.this.finish();
    }

    @Override
    public void doLoading() {
        mLoading.setVisibility(View.GONE);
    }

    @Override
    public void completeSuccess(ChangeStatusData data, String msg) {
        mLoading.setVisibility(View.VISIBLE);

        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.Extras.BOOKING, booking);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();

        Utilities.showToast(getApplicationContext(), msg);
    }

    @Override
    public void completeFailed(String msg) {
        mLoading.setVisibility(View.VISIBLE);
        Utilities.showToast(getApplicationContext(), msg);
    }
}
