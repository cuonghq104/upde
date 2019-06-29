package stp.cuonghq.upde.commons;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import stp.cuonghq.upde.R;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

/**
 * Created by cuong.hq1 on 5/3/2019.
 */

public class Utilities {

    private static final int DOLLAR_RATE = 23500;
    private static final String[] DAY_OF_WEEK = {"Thứ sáu", "Thứ sáu", "Thứ bảy", "Chủ nhật ", "Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm"};

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
        span.setSpan(new ForegroundColorSpan(getColor(mContext, R.color.colorBlack)), 0, title.length(), SPAN_INCLUSIVE_INCLUSIVE);
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

    public static String dateToStringStatisticStartDate(String type, String date) {
        String DEFAULT_START_TIME = " 00:01 am";
        switch (type) {
            case Constants.DateAndTime.TYPE_DAY:
                return date + DEFAULT_START_TIME;

            case Constants.DateAndTime.TYPE_MONTH:
                String[] tempSplit = date.split("/");
                return tempSplit[0] + "/01/" + tempSplit[1] + DEFAULT_START_TIME;

            case Constants.DateAndTime.TYPE_YEAR:
                return "01/01/" + date + DEFAULT_START_TIME;

            case Constants.DateAndTime.TYPE_WEEK:
                Log.d("hehe", "timeeeee " + date);
                String[] tempSplitwwbySpace = date.split(" ");
                String[] tempSplitwwbyDash = tempSplitwwbySpace[1].split("/");

                Calendar calendar = new GregorianCalendar();
                calendar.clear();
                calendar.set(Calendar.YEAR, Integer.parseInt(tempSplitwwbyDash[2]));
                calendar.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(tempSplitwwbySpace[0].substring(1)));
                return (
                        calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DATE) + "/" + calendar.get(Calendar.YEAR) + DEFAULT_START_TIME;

            default:
                return date + DEFAULT_START_TIME;
        }
    }

    public static String getDayOfWeek(String date) {
        String[] tempdatee = date.split("/");
        Calendar calendar = new GregorianCalendar();
        calendar.clear();
        calendar.set(Calendar.DATE, Integer.parseInt(tempdatee[1]));
        calendar.set(Calendar.MONTH, Integer.parseInt(tempdatee[0]));
        calendar.set(Calendar.YEAR, Integer.parseInt(tempdatee[2]));
        //Log.d("hehe","day "+calendar.get(Calendar.DAY_OF_WEEK));
        return DAY_OF_WEEK[calendar.get(Calendar.DAY_OF_WEEK)];
    }

    public static String dateToStringStatisticEndDate(String type, String date) {
        String DEFAULT_END_TIME = " 11:59 pm";
        switch (type) {
            case Constants.DateAndTime.TYPE_DAY:
                return date + DEFAULT_END_TIME;

            case Constants.DateAndTime.TYPE_MONTH:
                String[] tempSplit = date.split("/");
                Calendar mm = new GregorianCalendar(Integer.parseInt(tempSplit[1]), Integer.parseInt(tempSplit[0]) - 1, 1);
                return tempSplit[0] + "/" + mm.getActualMaximum(Calendar.DAY_OF_MONTH) + "/" + tempSplit[1] + DEFAULT_END_TIME;

            case Constants.DateAndTime.TYPE_YEAR:
                return "12/31/" + date + DEFAULT_END_TIME;

            case Constants.DateAndTime.TYPE_WEEK:
                String[] tempSplitwwbySpace = date.split(" ");
                String[] tempSplitwwbyDash = tempSplitwwbySpace[1].split("/");

                Calendar calendar = new GregorianCalendar();
                calendar.clear();
                calendar.set(Calendar.YEAR, Integer.parseInt(tempSplitwwbyDash[2]));
                calendar.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(tempSplitwwbySpace[0].substring(1)));
                calendar.add(Calendar.DAY_OF_YEAR, 6);
                return (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DATE) + "/" + calendar.get(Calendar.YEAR) + DEFAULT_END_TIME;
            default:
                return date + DEFAULT_END_TIME;
        }
    }

    public static String changeDollarToVietNam(String dollar) {
        if (dollar.equals("0")) {
            return dollar;
        }
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        DecimalFormat df = new DecimalFormat("###,###", symbols);
        return df.format(Math.round(Double.parseDouble(dollar) * DOLLAR_RATE));
    }
//            DateFormat df = new SimpleDateFormat("dd MM yyyy");
//                Date date = df.parse("02 26 1991");
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(date);

    //true là cần update , false là không cần
    public static boolean needUpdateDatabase(String mType, long current, String database) {
        Calendar curentDay = new GregorianCalendar();
        curentDay.clear();
        curentDay.setTimeInMillis(current);

        Calendar databaseDay = new GregorianCalendar();
        databaseDay.clear();
        switch (mType) {
            case Constants.DateAndTime.TYPE_DAY:
                String[] tt = database.split("/");
                databaseDay.set(Calendar.DATE, Integer.parseInt(tt[1]));
                databaseDay.set(Calendar.MONTH, Integer.parseInt(tt[0]));
                databaseDay.set(Calendar.YEAR, Integer.parseInt(tt[2]));

                Log.d("hehe ", "compare day databaseDay " + databaseDay.get(Calendar.DATE) + " with " + curentDay.get(Calendar.DATE) + " result " + databaseDay.compareTo(curentDay));

                return databaseDay.compareTo(curentDay) < 0;

            case Constants.DateAndTime.TYPE_MONTH:
                String[] tempSplit = database.split("/");

                Log.d("hehe ", "compare month database month " + database + " with " + curentDay.get(Calendar.MONTH));

                return Integer.parseInt(tempSplit[0]) < curentDay.get(Calendar.MONTH);

            case Constants.DateAndTime.TYPE_YEAR:
                Log.d("hehe ", "compare year database year " + database + " with " + curentDay.get(Calendar.YEAR));

                return Integer.parseInt(database) < curentDay.get(Calendar.YEAR);

            case Constants.DateAndTime.TYPE_WEEK:
                String[] tempSplitwwbySpace = database.split(" ");
                String[] tempSplitwwbyDash = tempSplitwwbySpace[1].split("/");

                databaseDay.set(Calendar.YEAR, Integer.parseInt(tempSplitwwbyDash[2]));
                databaseDay.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(tempSplitwwbySpace[0].substring(1)));
                databaseDay.add(Calendar.DAY_OF_YEAR, 6);

                Log.d("hehe ", "compare week database " + databaseDay.get(Calendar.WEEK_OF_YEAR) + " with " + curentDay.get(Calendar.WEEK_OF_YEAR));

                return databaseDay.get(Calendar.WEEK_OF_YEAR) < curentDay.get(Calendar.WEEK_OF_YEAR);
            default:
                return false;
        }
    }

    public static String getPathFromURI(Activity activity, Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static String getPath(final Context context, final Uri uri) {
        return UriUtilities.getPath(context, uri);
    }
}
