package stp.cuonghq.upde.commons;

import android.app.Application;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;
import android.content.SharedPreferences;
import android.util.Log;


public class UpdeApplication extends Application implements LifecycleObserver {

    private static final String TAG = UpdeApplication.class.toString();

    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void foreground() {
        Log.d(TAG, "upde::foreground");
        setApplicationState(true);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void background() {
        Log.d(TAG, "upde::background");
        setApplicationState(false);
    }

    public void setApplicationState(boolean active) {
        SharedPreferences sp = Utilities.getSP(getApplicationContext());
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Constants.SharePreferenceConstants.STATE, active);
        editor.apply();
    }
}
