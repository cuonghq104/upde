package stp.cuonghq.upde.screen.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.AppContext;
import stp.cuonghq.upde.commons.AppResource;
import stp.cuonghq.upde.commons.AppSharePreferences;
import stp.cuonghq.upde.commons.Constants;
import stp.cuonghq.upde.commons.Utilities;
import stp.cuonghq.upde.data.sources.local.AppDatabase;
import stp.cuonghq.upde.screen.container.HostContainerActivity;
import stp.cuonghq.upde.screen.container.SupplierContainerActivity;
import stp.cuonghq.upde.screen.start.StartActivity;

public class SplashActivity extends AppCompatActivity implements Contract.View {

    private static final String TAG = SplashActivity.class.toString();

    Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initData();
    }

    private void initData() {
        // Init singleton
        AppSharePreferences.getInstance(getApplicationContext());
        AppResource.getInstance(getApplicationContext());
        AppDatabase.getInstance(getApplicationContext());
        AppContext.getInstance(getApplicationContext());

        // Processing
        mPresenter = new Presenter();
        mPresenter.setView(SplashActivity.this);
        mPresenter.getFirebaseToken();
    }

    @Override
    public void getFirebaseTokenSuccess() {
        mPresenter.checkUserInformation();
    }

    @Override
    public void getFirebaseTokenFailed(String msg) {
        Utilities.showToast(getApplicationContext(), msg);
        Intent intent = StartActivity.getInstance(getApplicationContext());
        //Intent intent = SupplierContainerActivity.getInstance(getApplicationContext());
        startActivity(intent);
        SplashActivity.this.finish();
    }

    @Override
    public void informationValid() {
        String userType  = AppSharePreferences.getStringFromSP(Constants.SharePreferenceConstants.LOGIN_TYPE);
        Log.d("LOGIN_TYPE", userType);
        Intent intent;
        if(userType.equals(Constants.LOGIN_AS_SUPPLIER_TYPE)){
            intent = new Intent(SplashActivity.this, SupplierContainerActivity.class);
        } else {
            intent = new Intent(SplashActivity.this, HostContainerActivity.class);
        }

        //Intent intent = SupplierContainerActivity.getInstance(getApplicationContext());
        startActivity(intent);
        SplashActivity.this.finish();
    }

    @Override
    public void informationNotValid() {
        Intent intent = StartActivity.getInstance(getApplicationContext());
        startActivity(intent);
        SplashActivity.this.finish();
    }
}
