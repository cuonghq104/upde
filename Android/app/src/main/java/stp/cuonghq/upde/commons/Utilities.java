package stp.cuonghq.upde.commons;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import stp.cuonghq.upde.R;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

/**
 * Created by cuong.hq1 on 5/3/2019.
 */

public class Utilities {

    public static int getColor(Context mContext, int idColor) {
        int color = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            color = mContext.getColor(idColor);
        } else {
            color = mContext.getResources().getColor(idColor);
        }

        return color;
    }
    public static SpannableString emphasize(Context mContext, String title) {
        SpannableString span = new SpannableString(title);
        span.setSpan(new ForegroundColorSpan(getColor(mContext,R.color.colorBlack)), 0, title.length(), SPAN_INCLUSIVE_INCLUSIVE);
        span.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }

    public static void changeFragment(FragmentManager fm, int viewId, Fragment fragment) {
        fm.beginTransaction()
                .replace(viewId, fragment)
                .commit();
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showSnackBar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    public static SharedPreferences getSP(Context context) {
        return context.getSharedPreferences(Constants.SharePreferenceConstants.NAME, Constants.SharePreferenceConstants.MODE);
    }

    public static String convertToVnd(int value) {
        return NumberFormat.getNumberInstance(Locale.US).format(value) + " VND";
    }

    public static String dateToString(Date date) {
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
        return format1.format(date);
    }

    public static String formatDate(String pattern, String originalDate) {

        String currentPattern = Constants.DateAndTime.ORIGINAL_DATE;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(currentPattern);
        Date date = null;
        try {
            date = simpleDateFormat.parse(originalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        simpleDateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
        return simpleDateFormat.format(date);
    }

    public static String formatToAppDate(String originalDate) {
        return formatDate(Constants.DateAndTime.APP_DATE, originalDate);
    }

    public static String validateNotNullString(String input, String expectedOutput) {
        return (input == null) ? "" : expectedOutput;
    }

}
