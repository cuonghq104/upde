package stp.cuonghq.upde.screen.bookingdetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.AppContext;
import stp.cuonghq.upde.commons.AppToolbar;
import stp.cuonghq.upde.commons.BaseActivity;
import stp.cuonghq.upde.commons.Constants;
import stp.cuonghq.upde.commons.DisplayTextView;
import stp.cuonghq.upde.commons.DriverNoteDialog;
import stp.cuonghq.upde.commons.Utilities;
import stp.cuonghq.upde.data.models.BookingResp;
import stp.cuonghq.upde.data.models.ChangeStatusData;
import stp.cuonghq.upde.services.fcm.UpdeFCM;

public class BookingDetailActivity extends BaseActivity<BookingDetailActivity, Presenter> implements Contract.View, DriverNoteDialog.DriverNoteListener {

    public static Intent getInstance(Context context, BookingResp booking) {
        Intent intent = new Intent(context, BookingDetailActivity.class);
        intent.putExtra(Constants.Extras.BOOKING, booking);
        return intent;
    }

    public static Intent getInstance(Context context, UpdeFCM.Booking booking) {
        BookingResp resp = new BookingResp();
        resp.setEmailGuest(booking.getEmailguest());
        resp.setNameLeave(booking.getNameleave());
        resp.setNameArrive(booking.getNamearrive());
        resp.setIdTrip(booking.getIdTrip());
        resp.setNote(booking.getNote());
        resp.setPhoneNumber(booking.getPhonenumber());
        resp.setPrice(Double.parseDouble(booking.getPrice()));
        resp.setPriceVn(booking.getPriceVn());
        resp.setSerial(booking.getSerial());
        resp.setTimeLeave(booking.getTimeleave());
        resp.setTimeBook(booking.getTimeBook());
        resp.setNameCustomer(booking.getNameCustomer());
        resp.setVehicleType(booking.getVehicleType());
        resp.setFlightCode(booking.getFlightNo());
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
    @BindView(R.id.tv_phone)
    DisplayTextView mTvPhone;
    @BindView(R.id.tv_time_booking)
    DisplayTextView mTvTimeBooking;

    @BindView(R.id.btn_accept)
    AppCompatButton mBtnAccept;

    @BindView(R.id.toolbar)
    AppToolbar mToolbar;

    private BookingResp booking;
    private Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);
        ButterKnife.bind(this);
        initData();
        addListener();
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
        if (TextUtils.equals(booking.getFlightNo(), "none")) {
            mTvFlightNo.setVisibility(View.GONE);
        } else {
            mTvFlightNo.setVisibility(View.VISIBLE);
            mTvFlightNo.setContent(booking.getFlightNo());
        }

        mTvTicketNo.setContent(String.valueOf(booking.getSerial()));
        mTvName.setContent(booking.getNameCustomer());
        mTvPickUp.setContent(booking.getNameLeave());
        mTvDestination.setContent(booking.getNameArrive());
        mTvType.setContent(StringUtils.capitalize(booking.getVehicleType()));
        mTvTime.setContent(booking.getTimeLeave());
        mTvPrice.setContent(Utilities.convertToVnd(booking.getPriceVn()));
        mTvNote.setContent(booking.getNote());
        mTvEmail.setContent(booking.getEmailGuest());
        mTvPhone.setVisibility((booking.getPhoneNumber() == null || booking.getPhoneNumber().equals("")) ? View.GONE : View.VISIBLE);
        mTvPhone.setContent(booking.getPhoneNumber());
        mTvTimeBooking.setContent(booking.getTimeBook());
    }

    private void addListener() {
        mToolbar.setLeftBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookingDetailActivity.this.finish();
            }
        });
    }

    DriverNoteDialog dialog;

    @OnClick(R.id.btn_accept)
    void acceptBooking() {
        if (AppContext.getInstance() == null) {
            AppContext.getInstance(getApplicationContext());
        }

        dialog = new DriverNoteDialog(this, this);

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
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

    @Override
    public void onAccept(String note) {
        if (mPresenter != null) {
            mPresenter.confirm(booking.getIdTrip(), note);
        }
    }

    @Override
    public void onCancel() {
        if (dialog != null) {
            dialog.cancel();
        }
    }
}
