package stp.cuonghq.upde.commons;

import android.content.Context;

public class AppContext {

    private static Context mInstance;

    public static Context getInstance(Context mInstance) {
        if (AppContext.mInstance == null) {
            AppContext.mInstance = mInstance;
        }
        return mInstance;
    }

    public static Context getInstance() {
        return mInstance;
    }
}
