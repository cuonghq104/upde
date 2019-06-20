/*
 *
 *  Statistic layout for Delivery
 *
 * */
package stp.cuonghq.upde.screen.container;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.Constants;
import stp.cuonghq.upde.commons.NonPresenterActivity;
import stp.cuonghq.upde.data.models.BookingResp;
import stp.cuonghq.upde.screen.home.HomeFragment;
import stp.cuonghq.upde.screen.profile.ProfileFragment;
import stp.cuonghq.upde.screen.start.StartActivity;
import stp.cuonghq.upde.screen.statistic.host.StatisticFragment;
import stp.cuonghq.upde.screen.statistic.supplier.SupplierStatisticFragment;
import stp.cuonghq.upde.services.fcm.UpdeFCM;

public class SupplierContainerActivity extends NonPresenterActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigation;

    @BindView(R.id.tv_toolbar)
    AppCompatTextView tvToolbar;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private HomeFragment mHomeFragment;
    private SupplierStatisticFragment mStatisticFragment;
    private ProfileFragment mProfileFragment;
    private BroadcastReceiver mReceiver;
    private NotificationReceiver mBookingReceive;
    private NotificationReceiver mConfirmReceive;

    public void setBookingReceive(NotificationReceiver mBookingReceive) {
        this.mBookingReceive = mBookingReceive;
    }

    public void setConfirmReceive(NotificationReceiver mConfirmReceive) {
        this.mConfirmReceive = mConfirmReceive;
    }

    public static Intent getInstance(Context context) {
        Intent intent = new Intent(context, SupplierContainerActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        initView();
        setupUI();
        addListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("service.to.activity.transfer");
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    String json = intent.getStringExtra(Constants.Extras.BOOKING);
                    UpdeFCM.Booking booking = new Gson().fromJson(json, UpdeFCM.Booking.class);
                    if (mBookingReceive != null ) {
                        BookingResp resp = new BookingResp();

                        resp.setRead(false);
                        resp.setEmailguest(booking.getEmailguest());
                        resp.setNameLeave(booking.getNameleave());
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
                        resp.setFlightNo(booking.getFlightNo());

                        if (TextUtils.equals(booking.getType(), "notify_accept")) {
                            mConfirmReceive.receiveBooking(resp);
                        } else {
                            mBookingReceive.receiveBooking(resp);
                        }
                    }
                }
            }
        };
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    private void setupUI() {
        changeFragment(mHomeFragment);
    }

    private void initView() {
        ButterKnife.bind(this);

        mHomeFragment = new HomeFragment();
        mProfileFragment = new ProfileFragment();
        mStatisticFragment = new SupplierStatisticFragment();
    }

    private void addListener() {
        mBottomNavigation.setOnNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_home:
                changeFragment(mHomeFragment);
                mToolbar.setVisibility(View.VISIBLE);
                break;
            case R.id.menu_stat:
                changeFragment(mStatisticFragment);
                mToolbar.setVisibility(View.GONE);
                break;
            case R.id.menu_profile:
                changeFragment(mProfileFragment);
                mToolbar.setVisibility(View.GONE);
                break;
        }
        return true;
    }

    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, fragment)
                .commit();

        if (fragment instanceof HomeFragment) {
            tvToolbar.setText(R.string.title_notification);
        } else if (fragment instanceof ProfileFragment) {
            tvToolbar.setText(R.string.title_profile);
        } else {
            tvToolbar.setText(getText(R.string.title_launching_soon));
        }
    }

    public void logout() {
        Intent intent = StartActivity.getInstance(getApplicationContext());
        startActivity(intent);
        SupplierContainerActivity.this.finish();
    }
}
