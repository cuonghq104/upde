package stp.cuonghq.upde.screen.statistic.host;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.Constants;
import stp.cuonghq.upde.commons.Utilities;
import stp.cuonghq.upde.data.models.BookingResp;
import stp.cuonghq.upde.screen.confirmdetail.ConfirmDetailActivity;

/**
 * Created by cuong.hq1 on 5/31/2019.
 */

public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.StatisticViewHolder>{
    private final String TAG = "StatisticAdapter";
    private final String title1 = " đã đặt một chuyến xe từ ";
    private final String title2 = " đến ";
    private final String TIME_TITLE = "Thời gian đi: ";

    //private ArrayList<String> mList = new ArrayList<>();
    private List<BookingResp> mList;
    private Fragment mFragment;
    private Context mContext;
    StatisticAdapter (Fragment mFragment) {
        this.mFragment = mFragment;
        this.mContext = mFragment.getContext();
    }

    public void setList(List<BookingResp> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StatisticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_statistic_item,parent,false);

        return new StatisticViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticViewHolder holder, int position) {
        //holder.tv_sample.setText("item "+position);
        final BookingResp booking = mList.get(position);
        if ("sedan".equalsIgnoreCase(booking.getVehicleType())) {
            holder.sta_imv_type.setImageResource(R.drawable.group_2);
            holder.sta_tv_type.setText("5 " + mContext.getString(R.string.title_seat));
        } else if ("suv".equalsIgnoreCase(booking.getVehicleType())) {
            holder.sta_imv_type.setImageResource(R.drawable.suv);
            holder.sta_tv_type.setText("7 " + mContext.getString(R.string.title_seat));
        } else {
            holder.sta_imv_type.setImageResource(R.drawable.minivan);
            holder.sta_tv_type.setText("16 " + mContext.getString(R.string.title_seat));
        }

        String email = booking.getEmailGuest();
        String pickup = booking.getNameLeave();
        String destination = booking.getNameArrive();
        String timeLeave = booking.getTimeLeave();
        int price = booking.getPriceVn();
        Log.d(TAG, "Data: " + email + " " + pickup + " " + destination + " " + timeLeave + " " + price);

        holder.sta_tv_content.setText((email != null) ? Utilities.emphasize(mContext, email) : "");
        holder.sta_tv_content.append(title1);
        holder.sta_tv_content.append((pickup != null) ? Utilities.emphasize(mContext, pickup) : "");
        holder.sta_tv_content.append(title2);
        holder.sta_tv_content.append((destination != null) ? Utilities.emphasize(mContext, destination) : "");

        holder.sta_tv_time_title.setText(TIME_TITLE);
        holder.sta_tv_time.setText(timeLeave);

        holder.sta_tv_price.setText(price + " vnd");

        holder.setOnclickItemStatistic(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("hehe","click");
                Intent mIntentStatistic = ConfirmDetailActivity.getInstance(mFragment.getContext(),booking,Constants.Extras.COMPLETE);
                mFragment.startActivityForResult(mIntentStatistic, Constants.STATISTIC_REQUEST_CODE);
            }
        });

//        holder.sta_imv_type.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent mIntentStatistic = ConfirmDetailActivity.getInstance(mFragment.getContext(),booking,Constants.Extras.COMPLETE);
//                mFragment.startActivityForResult(mIntentStatistic, Constants.STATISTIC_REQUEST_CODE);
//                Log.d("hehe","click img");
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return (mList == null) ? 0 : mList.size();
    }

    class StatisticViewHolder extends RecyclerView.ViewHolder{
        //TextView tv_sample;
        CircleImageView sta_imv_type;
        AppCompatTextView sta_tv_type, sta_tv_content, sta_tv_time_title, sta_tv_time, sta_tv_price;
        View.OnClickListener listener;

        public StatisticViewHolder(View itemView) {
            super(itemView);
            //tv_sample = itemView.findViewById(R.id.tv_statistic_sample);
            sta_tv_type = itemView.findViewById(R.id.sta_tv_type);
            sta_tv_content = itemView.findViewById(R.id.sta_tv_content);
            sta_tv_time_title = itemView.findViewById(R.id.sta_tv_time_title);
            sta_tv_time = itemView.findViewById(R.id.sta_tv_time);
            sta_tv_price = itemView.findViewById(R.id.sta_tv_price);
            sta_imv_type = itemView.findViewById(R.id.sta_imv_type);
        }

        public void setOnclickItemStatistic(View.OnClickListener listener){
            this.listener = listener;
            itemView.setOnClickListener(listener);
        }


    }
}
