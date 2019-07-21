package stp.cuonghq.upde.screen.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import stp.cuonghq.upde.R;
import stp.cuonghq.upde.data.models.BookingResp;
import stp.cuonghq.upde.screen.home.booking.BookingFragment;
import stp.cuonghq.upde.screen.home.complete.CompleteFragment;
import stp.cuonghq.upde.screen.home.confirm.ConfirmFragment;

/**
 * Created by cuong.hq1 on 5/3/2019.
 */

public class HomePagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = HomePagerAdapter.class.toString();
    private Context mContext;
    private BookingFragment bookingFragment;
    private ConfirmFragment confirmFragment;
    private CompleteFragment completeFragment;

    public HomePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
        bookingFragment = new BookingFragment();
        confirmFragment = new ConfirmFragment();
        completeFragment = new CompleteFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return bookingFragment;
            case 1:
                return confirmFragment;
            case 2:
                return completeFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.title_booking).toUpperCase();
            case 1:
                return mContext.getString(R.string.title_confirm).toUpperCase();
            case 2:
                return mContext.getString(R.string.title_complete).toUpperCase();
            default:
                return null;
        }
    }



    public void addToBookingList(BookingResp booking) {
        Log.d(TAG, "addToBookingList-adapter");
        bookingFragment.addToBookingList(booking);
    }

    public void removeFromBookingList(BookingResp booking) {
        bookingFragment.removeFromBookingList(booking);
    }

    public void addToConfirmList(BookingResp booking) {
        confirmFragment.addToConfirmList(booking);
    }

    public void removeFromConfirmList(BookingResp booking) {
        confirmFragment.removeFromConfirmList(booking);
    }

    public void addToCompleteList(BookingResp booking) {
        completeFragment.addToCompleteList(booking);
    }
}
