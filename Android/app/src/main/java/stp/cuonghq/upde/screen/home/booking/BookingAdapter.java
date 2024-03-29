package stp.cuonghq.upde.screen.home.booking;

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
import stp.cuonghq.upde.screen.bookingdetail.BookingDetailActivity;

/**
 * Created by cuong.hq1 on 5/3/2019.
 */

public class BookingAdapter extends RecyclerView.Adapter<BookingViewHolder> {

    private Context mContext;
    private List<BookingResp> mList;
    private Fragment mFragment;

    public BookingAdapter(Fragment mFragment) {
        this.mFragment = mFragment;
        this.mContext = mFragment.getContext();
    }

    public void setList(List<BookingResp> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void remove(int pos) {
        if (mList != null && mList.size() > pos) {
            mList.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos, getItemCount());
        }
    }

    public void remove(BookingResp resp) {
        int pos = -1;
        for (BookingResp bookingResp : mList) {
            if (resp.getIdTrip().equals(bookingResp.getIdTrip())) {
                remove(mList.indexOf(bookingResp));
                return;
            }
        }
    }

    @Override
    public BookingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_booking_item, parent, false);
        return new BookingViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(final BookingViewHolder holder, int position) {
        final BookingResp booking = mList.get(position);
        holder.bind(booking, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                holder.goToDetail();
                Intent intent = BookingDetailActivity.getInstance(mFragment.getContext(), booking);
                mFragment.startActivityForResult(intent, Constants.BOOKING_REQUEST_CODE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mList == null) ? 0 : mList.size();
    }

}
