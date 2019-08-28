package stp.cuonghq.upde.screen.home.confirm;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import stp.cuonghq.upde.R;
import stp.cuonghq.upde.data.models.BookingList;
import stp.cuonghq.upde.data.models.BookingResp;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Contract.View {

    View mLayout;
    @BindView(R.id.layout_loading)
    View mLoading;
    @BindView(R.id.rv_confirm)
    RecyclerView mRvConfirm;
    @BindView(R.id.srf_refresh)
    SwipeRefreshLayout mRefresh;

    private ConfirmAdapter mAdapter;
    private List<BookingResp> mList;
    private LinearLayoutManager mLayoutManager;

    private Presenter mPresenter;

    public ConfirmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mLayout = inflater.inflate(R.layout.fragment_confirm, container, false);
        ButterKnife.bind(this, mLayout);
        initData();
        setupUI();
        return mLayout;
    }

    private void setupUI() {
        mRefresh.setOnRefreshListener(this);
    }

    private void initData() {
        mPresenter = new Presenter();
        mPresenter.setView(ConfirmFragment.this);
        mList = new ArrayList<>();

        mLayoutManager = new LinearLayoutManager(getContext());
        mRvConfirm.setLayoutManager(mLayoutManager);

        mAdapter = new ConfirmAdapter(ConfirmFragment.this);
        mAdapter.setList(mList);
        mRvConfirm.setAdapter(mAdapter);

        mPresenter.loadConfirmList();
    }

    public void addToConfirmList(BookingResp booking) {
        mList.add(0, booking);
        mAdapter.notifyItemInserted(0);
        mLayoutManager.scrollToPositionWithOffset(0, 0);
    }

    public void removeFromConfirmList(BookingResp booking) {
        if (mList != null) {
            mAdapter.remove(booking);
        }
    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//
//            if (requestCode == Constants.CONFIRM_REQUEST_CODE) {
//
//                BookingResp statusData = (BookingResp) data.getSerializableExtra(Constants.Extras.BOOKING);
//                if (statusData != null) {
//                    int pos = -1;
//                    for (int i = 0; i < mList.size(); i++) {
//                        if (mList.get(i).getIdTrip().equalsIgnoreCase(statusData.getIdTrip())) {
//                            pos = i;
//                        }
//                    }
//                    if (pos != -1) {
//                        BookingResp booking = mList.get(pos);
//                        mList.remove(pos);
//                        mAdapter.notifyItemRemoved(pos);
//                        moveToCompleteList(booking);
//                    }
//                }
//
//            }
//        }
//    }
//
//    private void moveToCompleteList(BookingResp booking) {
//        Fragment parentFragment = getParentFragment();
//        if (parentFragment instanceof HomeFragment) {
//
//            ((HomeFragment) parentFragment).moveToCompleteList(booking);
//        }
//
//    }


    @Override
    public void onRefresh() {
        mRefresh.setRefreshing(false);
        if (mPresenter != null) {
            mPresenter.loadConfirmList();
        }
    }

    @Override
    public void doLoading() {
        mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void getConfirmListSuccess(BookingList bookingList) {
        mList = bookingList.getList();
        mLoading.setVisibility(View.GONE);
        mAdapter.setList(bookingList.getList());
    }

    @Override
    public void getConfirmListFailed(String msg) {
        mLoading.setVisibility(View.GONE);
    }
}
