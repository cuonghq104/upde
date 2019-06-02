package stp.cuonghq.upde.screen.home.booking;

import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.Utilities;
import stp.cuonghq.upde.data.models.BookingResp;

/**
 * Created by cuong.hq1 on 5/4/2019.
 */

public class BookingViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_time)
    AppCompatTextView mTvTime;
    @BindView(R.id.tv_price)
    AppCompatTextView mTvPrice;
    @BindView(R.id.tv_content)
    AppCompatTextView mTvContent;
    @BindView(R.id.imv_type)
    CircleImageView imvType;
    @BindView(R.id.tv_type)
    AppCompatTextView mTvType;
    @BindView(R.id.tv_time_title)
    AppCompatTextView tvTimeTitle;

    View.OnClickListener listener;
    BookingResp booking;

    public BookingViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    private void addListener() {
        itemView.setOnClickListener(listener);
    }

    public void bind(BookingResp booking, View.OnClickListener listener) {
        this.bind(booking, listener, false);
    }

    public void bind(BookingResp booking, View.OnClickListener listener, boolean isComplete) {
        this.booking = booking;
        this.listener = listener;
        setupUI(isComplete);
        addListener();
    }

    private void setupUI(boolean isComplete) {

//        if (booking.isRead()) {
//            itemView.setBackgroundColor(Utilities.getColor(itemView.getContext(), R.color.colorWhite));
//        } else {
//            itemView.setBackgroundColor(Utilities.getColor(itemView.getContext(), R.color.colorEmphasize));
//        }

        if ("sedan".equalsIgnoreCase(booking.getVehicleType())) {
            imvType.setImageResource(R.drawable.group_2);
            mTvType.setText("5 " + itemView.getContext().getString(R.string.title_seat));
        } else if ("suv".equalsIgnoreCase(booking.getVehicleType())) {
            imvType.setImageResource(R.drawable.suv);
            mTvType.setText("7 " + itemView.getContext().getString(R.string.title_seat));
        } else {
            imvType.setImageResource(R.drawable.minivan);
            mTvType.setText("16 " + itemView.getContext().getString(R.string.title_seat));
        }

        mTvTime.setText(booking.getTimeleave());
        mTvPrice.setText(Utilities.convertToVnd(booking.getPriceVn()));

        String title1 = " đã đặt một chuyến xe từ ";
        String title2 = " đến ";
        String title3 = ". Ghi chú: ";

        String email = booking.getEmailguest();
        String pickup = booking.getNameHome();
        String destination = booking.getNameArrive();
        String note = booking.getNote();

        mTvContent.setText((email != null) ? Utilities.emphasize(itemView.getContext(), email) : "");
        mTvContent.append(title1);
        mTvContent.append((pickup != null) ? Utilities.emphasize(itemView.getContext(), pickup) : "");
        mTvContent.append(title2);
        mTvContent.append((destination != null) ? Utilities.emphasize(itemView.getContext(), destination) : "");
        mTvContent.append(title3);

        mTvContent.append(Utilities.emphasize(itemView.getContext(), note));

        if (isComplete) {
            int color = 0;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                color = itemView.getContext().getColor(R.color.colorGreen);
            } else {
                color = itemView.getContext().getResources().getColor(R.color.colorGreen);
            }
            tvTimeTitle.setTextColor(color);
            tvTimeTitle.setText(StringUtils.capitalize(itemView.getContext().getString(R.string.title_complete)));
        }
    }

    public void goToDetail() {
        if (!booking.isRead()) {
            itemView.setBackgroundColor(Utilities.getColor(itemView.getContext(), R.color.colorWhite));
        }
    }
}
