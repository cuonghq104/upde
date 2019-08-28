package com.example.upde_sms.screens.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.upde_sms.R;
import com.example.upde_sms.commons.CommonCallbacks;
import com.example.upde_sms.commons.Constants;
import com.example.upde_sms.commons.Utitlities;
import com.example.upde_sms.screens.home.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.apache.commons.lang3.StringUtils;

public class SplashActivity extends AppCompatActivity {

    public static final String TAG = SplashActivity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getFirebaseToken();
    }

    private void getFirebaseToken() {
        final SharedPreferences sp = getSharedPreferences("Upde-SMS", MODE_PRIVATE);
        String token = sp.getString("token", "");
        if (!StringUtils.equals(token, "")) {
            Log.d(TAG, "Firebase Token: " + token);
            selfCheckPermission();
        } else {
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "getInstanceId failed", task.getException());
                                return;
                            }
                            String token = task.getResult().getToken();
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("token", token);

                            Log.d(TAG, "Firebase Token: " + token);
                            selfCheckPermission();
                        }
                    });
        }
    }

    private void selfCheckPermission() {
        Utitlities.checkPermission(new CommonCallbacks.RequestPermissionCallback() {
            @Override
            public void permissionGranted() {
                Intent intent = MainActivity.getInstance(SplashActivity.this);
                startActivity(intent);
            }

            @Override
            public void permissionDenied() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(Constants.PERMISSION_NEEDED, Constants.REQUEST_PERMISSION_CODE);
                }
            }
        }, getApplicationContext(), Constants.PERMISSION_NEEDED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Utitlities.checkPermission(new CommonCallbacks.RequestPermissionCallback() {
            @Override
            public void permissionGranted() {
                Intent intent = MainActivity.getInstance(SplashActivity.this);
                startActivity(intent);
            }

            @Override
            public void permissionDenied() {
                SplashActivity.this.finish();
            }
        }, getApplicationContext(), Constants.PERMISSION_NEEDED);
    }
}
