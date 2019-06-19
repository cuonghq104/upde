/*
*
* Statistic layout for Host
*
* */
package stp.cuonghq.upde.screen.container;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.NonPresenterActivity;
import stp.cuonghq.upde.screen.profile.ProfileFragment;
import stp.cuonghq.upde.screen.start.StartActivity;
import stp.cuonghq.upde.screen.statistic.host.StatisticFragment;

public class HostContainerActivity extends NonPresenterActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.bottom_navigation_host)
    BottomNavigationView mBottomNavigation;

    private StatisticFragment mStatisticFragment;
    private ProfileFragment mProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_host);
        initView();
        setupUI();
        addListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    private void setupUI() {
        changeFragment(mStatisticFragment);
    }

    private void initView() {
        ButterKnife.bind(this);
        mProfileFragment = new ProfileFragment();
        mStatisticFragment = new StatisticFragment();
    }

    private void addListener() {
        mBottomNavigation.setOnNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_stat_host:
                changeFragment(mStatisticFragment);
                break;
            case R.id.menu_profile_host:
                changeFragment(mProfileFragment);
                break;
        }
        return true;
    }

    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container_host, fragment)
                .commit();
    }

    public void logout() {
        Intent intent = StartActivity.getInstance(getApplicationContext());
        startActivity(intent);
        HostContainerActivity.this.finish();
    }
}
