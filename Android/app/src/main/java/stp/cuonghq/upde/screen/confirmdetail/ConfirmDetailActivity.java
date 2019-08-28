package stp.cuonghq.upde.screen.confirmdetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.AppToolbar;
import stp.cuonghq.upde.commons.Constants;
import stp.cuonghq.upde.commons.DisplayTextView;
import stp.cuonghq.upde.commons.NonPresenterActivity;
import stp.cuonghq.upde.commons.Utilities;
import stp.cuonghq.upde.data.models.BookingResp;
import stp.cuonghq.upde.data.models.ChangeStatusData;

public class ConfirmDetailActivity extends NonPresenterActivity implements Contract.View {

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
    @BindView(R.id.tv_complete_time)
    DisplayTextView mTvTimeComplete;
    @BindView(R.id.tv_time_booking)
    DisplayTextView mTvTimeBooking;
    @BindView(R.id.tv_note)
    DisplayTextView mTvNote;
    @BindView(R.id.tv_note_for_driver)
    DisplayTextView mTvNoteForDriver;

    @BindView(R.id.btn_complete)
    AppCompatButton mBtnComplete;
    @BindView(R.id.tv_complete)
    AppCompatTextView mTvComplete;

    @BindView(R.id.toolbar)
    AppToolbar mToolbar;

    BookingResp booking;
    int operation;
    Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_detail);
        initView();
        initData();
        addListener();
    }

    private void addListener() {
        mToolbar.setLeftBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDetailActivity.this.finish();
            }
        });
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
        if (TextUtils.equals(booking.getFlightNo(), "none")) {
            mTvFlightNo.setVisibility(View.GONE);
        } else {
            mTvFlightNo.setVisibility(View.VISIBLE);
            mTvFlightNo.setContent(booking.getFlightNo());
        }
        mTvTicketNo.setContent(booking.getSerial() + "");
        mTvName.setContent(booking.getNameCustomer());
        mTvPickupAddress.setContent(booking.getNameArrive());
        mTvDestination.setContent(booking.getNameLeave());
        mTvType.setContent(StringUtils.capitalize(booking.getVehicleType()));
        mTvPickupTime.setContent(booking.getTimeLeave());
        mTvPrice.setContent(Utilities.convertToVnd(booking.getPriceVn()));
        mTvEmail.setContent(booking.getEmailGuest());

        mTvPhone.setVisibility((booking.getPhoneNumber() == null || booking.getPhoneNumber().equals("")) ? View.GONE : View.VISIBLE);
        mTvPhone.setContent(booking.getPhoneNumber());

        mTvNote.setContent(booking.getNote());
        mTvTimeBooking.setContent(booking.getTimeBook());
        mTvTimeComplete.setContent(booking.getTimeCompleted());

        mTvNoteForDriver.setVisibility(booking.getNoteForHost() == null ||
                booking.getNoteForHost().equalsIgnoreCase("") ? View.GONE : View.VISIBLE);
        mTvNoteForDriver.setContent(booking.getNoteForHost());

        if (operation == Constants.Extras.COMPLETE) {
            mBtnComplete.setVisibility(View.GONE);
            mTvComplete.setVisibility(View.VISIBLE);
            mBtnComplete.setOnClickListener(null);
            mTvTimeComplete.setVisibility(View.VISIBLE);
            mTvTimeBooking.setVisibility(View.GONE);

            mToolbar.setTitle(getResources().getString(R.string.title_complete_detail));

        } else if (operation == Constants.Extras.CONFIRM) {
            mBtnComplete.setVisibility(View.VISIBLE);
            mTvComplete.setVisibility(View.GONE);
            mTvTimeComplete.setVisibility(View.GONE);
            mTvTimeBooking.setVisibility(View.VISIBLE);

            mToolbar.setTitle(getResources().getString(R.string.title_confirm_detail));

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
