package stp.cuonghq.upde.screen.home.confirm;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
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

public class ConfirmAdapter extends RecyclerView.Adapter<BookingViewHolder> {

    private Context mContext;
    private List<BookingResp> mList;
    private Fragment mFragment;

    public ConfirmAdapter(Fragment mFragment) {
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
//        booking.setTimeleave(Utilities.formatDate(Constants.DateAndTime.APP_DATE, booking.getTimeleave()));
        holder.bind(booking, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ConfirmDetailActivity.getInstance(mContext, booking);
                mFragment.startActivityForResult(intent, Constants.CONFIRM_REQUEST_CODE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mList == null) ? 0 : mList.size();
    }

}
