package stp.cuonghq.upde.commons;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by cuong.hq1 on 5/16/2019.
 */

public class AppResource {

    private static Resources sInstance;

    public static Resources getInstance(Context context) {
        if (context != null) {
            sInstance = context.getResources();
        }
        return sInstance;
    }

    public static Resources getInstance() {
        return getInstance(null);
    }
}
