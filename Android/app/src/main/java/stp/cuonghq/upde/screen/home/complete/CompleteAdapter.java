package stp.cuonghq.upde.screen.home.complete;

import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.Constants;
import stp.cuonghq.upde.data.models.BookingResp;
import stp.cuonghq.upde.screen.confirmdetail.ConfirmDetailActivity;
import stp.cuonghq.upde.screen.home.booking.BookingViewHolder;

/**
 * Created by cuong.hq1 on 5/4/2019.
 */

public class CompleteAdapter extends RecyclerView.Adapter<BookingViewHolder> {

    private Context mContext;
    private List<BookingResp> mList;
    private Fragment mFragment;

    public CompleteAdapter(Fragment mFragment) {
        this.mFragment = mFragment;
        this.mContext = mFragment.getContext();
    }

    public void setList(List<BookingResp> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public BookingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_booking_item, parent, false);
        return new BookingViewHolder(inflater);

    }

    @Override
    public void onBindViewHolder(BookingViewHolder holder, int position) {
        final BookingResp booking = mList.get(position);
//        booking.setTimeLeave(Utilities.formatDate(Constants.DateAndTime.APP_DATE, booking.getTimeLeave()));
        holder.bind(booking, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ConfirmDetailActivity.getInstance(mContext, booking, Constants.Extras.COMPLETE);
                mFragment.startActivityForResult(intent, Constants.CONFIRM_REQUEST_CODE);
            }
        }, true);
    }

    @Override
    public int getItemCount() {
        return (mList == null) ? 0 : mList.size();
    }
}
