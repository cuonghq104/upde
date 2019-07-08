package stp.cuonghq.upde.screen.home.booking;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.Constants;
import stp.cuonghq.upde.commons.Utilities;
import stp.cuonghq.upde.data.models.BookingList;
import stp.cuonghq.upde.data.models.BookingResp;
import stp.cuonghq.upde.screen.home.HomeFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Contract.View {

    private static final String TAG = BookingFragment.class.toString();
    View mLayout;
    @BindView(R.id.rv_booking)
    RecyclerView mRvBooking;
    @BindView(R.id.srf_refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.layout_loading)
    View mLoading;
    private BookingAdapter mAdapter;
    private List<BookingResp> mList;
    private Presenter mPresenter;
    private LinearLayoutManager mLayoutManager;

    public BookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mLayout = inflater.inflate(R.layout.fragment_booking, container, false);
        ButterKnife.bind(this, mLayout);
        initData();
        setupUI();
        return mLayout;
    }

    private void setupUI() {
        mRefresh.setOnRefreshListener(this);
    }

    private void initData() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRvBooking.setLayoutManager(mLayoutManager);
        mList = new ArrayList<>();

        mPresenter = new Presenter();
        mPresenter.setView(BookingFragment.this);
        mAdapter = new BookingAdapter(BookingFragment.this);
        mAdapter.setList(mList);
        mRvBooking.setAdapter(mAdapter);

        mPresenter.getBookingList();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == Constants.BOOKING_REQUEST_CODE) {

                BookingResp booking = (BookingResp) data.getSerializableExtra(Constants.Extras.BOOKING);
                if (booking != null) {
                    int pos = -1;
                    for (int i = 0; i < mList.size(); i++) {
                        if (mList.get(i).getIdTrip().equalsIgnoreCase(booking.getIdTrip())) {
                            pos = i;
                        }
                    }
                    if (pos != -1) {
                        BookingResp resp = mList.get(pos);
                        mAdapter.remove(pos);

                    }
                }
            }
        }
    }

    private void moveToConfirmList(BookingResp booking) {

        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof HomeFragment) {

            ((HomeFragment) parentFragment).moveToConfirmList(booking);
        }
    }

    @Override
    public void onRefresh() {
        mRefresh.setRefreshing(false);
        if (mPresenter != null) {
            mPresenter.getBookingList();
        }
    }

    @Override
    public void doGetBookingList() {
        mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void getBookingListSuccess(BookingList list) {
        mList = list.getList();
        mLoading.setVisibility(View.GONE);
        mAdapter.setList(list.getList());
    }

    @Override
    public void getBookingListFailed(String msg) {
        mLoading.setVisibility(View.GONE);
        Utilities.showToast(getContext(), msg);
    }

    public void addToBookingList(BookingResp booking) {
//        Log.d(TAG, "addToBookingList");
        mPresenter.addToUnreadTripList(booking.getIdTrip());
        if (mList != null) {
            mList.add(0, booking);
            mAdapter.notifyItemInserted(0);
            mLayoutManager.scrollToPositionWithOffset(0, 0);
        }
    }

    public void removeFromBookingList(BookingResp booking) {
        if (mList != null) {
            mAdapter.remove(booking);
        }
    }
}
