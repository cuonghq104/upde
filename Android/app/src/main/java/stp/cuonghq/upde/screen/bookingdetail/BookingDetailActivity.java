package stp.cuonghq.upde.screen.bookingdetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.BaseActivity;
import stp.cuonghq.upde.commons.Constants;
import stp.cuonghq.upde.commons.DisplayTextView;
import stp.cuonghq.upde.commons.Utilities;
import stp.cuonghq.upde.data.models.BookingResp;
import stp.cuonghq.upde.data.models.ChangeStatusData;
import stp.cuonghq.upde.services.fcm.UpdeFCM;

public class BookingDetailActivity extends BaseActivity<BookingDetailActivity, Presenter> implements Contract.View {

    public static Intent getInstance(Context context, BookingResp booking) {
        Intent intent = new Intent(context, BookingDetailActivity.class);
        intent.putExtra(Constants.Extras.BOOKING, booking);
        return intent;
    }

    public static Intent getInstance(Context context, UpdeFCM.Booking booking) {
        BookingResp resp = new BookingResp();
        resp.setEmailguest(booking.getEmailguest());
        resp.setNameHome(booking.getNameleave());
        resp.setNameArrive(booking.getNamearrive());
        resp.setIdTrip(booking.getIdTrip());
        resp.setNote(booking.getNote());
        resp.setPhonenumber(booking.getPhonenumber());
        resp.setPrice(Double.parseDouble(booking.getPrice()));
        resp.setPriceVn(booking.getPriceVn());
        resp.setSerial(booking.getSerial());
        resp.setTimeleave(booking.getTimeleave());
        resp.setNameCustomer(booking.getNameCustomer());
        resp.setVehicleType(booking.getVehicleType());
        return getInstance(context, resp);
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
    DisplayTextView mTvPickUp;
    @BindView(R.id.tv_destination)
    DisplayTextView mTvDestination;
    @BindView(R.id.tv_type)
    DisplayTextView mTvType;
    @BindView(R.id.tv_time_pick_up)
    DisplayTextView mTvTime;
    @BindView(R.id.tv_price)
    DisplayTextView mTvPrice;
    @BindView(R.id.tv_note)
    DisplayTextView mTvNote;
    @BindView(R.id.tv_email)
    DisplayTextView mTvEmail;
    @BindView(R.id.btn_accept)
    AppCompatButton mBtnAccept;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.btn_back)
    AppCompatImageButton mBtnBack;

    private BookingResp booking;
    private Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    protected Presenter initPresenter() {
        mPresenter = new Presenter();
        mPresenter.setView(BookingDetailActivity.this);
        return mPresenter;
    }


    private void initData() {
        booking = (BookingResp) getIntent().getSerializableExtra(Constants.Extras.BOOKING);
        if (!booking.isRead()) {
            mPresenter.removeFromUnreadList(booking.getIdTrip());
        }
        setupUI();
    }

    private void setupUI() {
        if (TextUtils.equals(booking.getFlightNo(), null)) {
            mTvFlightNo.setVisibility(View.GONE);
        } else {
            mTvFlightNo.setVisibility(View.VISIBLE);
            mTvFlightNo.setContent(booking.getFlightNo());
        }

        mTvTicketNo.setContent(String.valueOf(booking.getSerial()));
        mTvName.setContent(booking.getNameCustomer());
        mTvPickUp.setContent(booking.getNameHome());
        mTvDestination.setContent(booking.getNameArrive());
        mTvType.setContent(StringUtils.capitalize(booking.getVehicleType()));
        mTvTime.setContent(booking.getTimeleave());
        mTvPrice.setContent(Utilities.convertToVnd(booking.getPriceVn()));
        mTvNote.setContent(booking.getNote());
        mTvEmail.setContent(booking.getEmailguest());
    }

    @OnClick(R.id.btn_accept)
    void acceptBooking() {
        if (mPresenter != null) {
            mPresenter.confirm(booking.getIdTrip());
        }

    }

    @OnClick(R.id.btn_back)
    void back() {
        BookingDetailActivity.this.finish();
    }

    @Override
    public void doLoading() {
        mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void confirmSuccess(ChangeStatusData data, String msg) {
        mLoading.setVisibility(View.GONE);
        finish();
        Utilities.showToast(getApplicationContext(), msg);
    }

    @Override
    public void confirmFailed(String msg) {
        mLoading.setVisibility(View.GONE);
        Utilities.showToast(getApplicationContext(), msg);

        Intent returnIntent = new Intent();
        returnIntent.putExtra(Constants.Extras.RESULT, Constants.Extras.ACCEPT_BY_ANOTHER_USER);
        returnIntent.putExtra(Constants.Extras.BOOKING, booking);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
