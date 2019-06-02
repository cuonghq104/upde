package stp.cuonghq.upde.screen.home.complete;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.Utilities;
import stp.cuonghq.upde.data.models.BookingList;
import stp.cuonghq.upde.data.models.BookingResp;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompleteFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Contract.View {

    View mLayout;
    @BindView(R.id.layout_loading)
    View mLoading;
    @BindView(R.id.rv_complete)
    RecyclerView mRvComplete;
    @BindView(R.id.srf_refresh)
    SwipeRefreshLayout mRefresh;

    private CompleteAdapter mAdapter;
    private List<BookingResp> mList;
    private Presenter mPresenter;

    private LinearLayoutManager mLayoutManager;

    public CompleteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mLayout = inflater.inflate(R.layout.fragment_complete, container, false);
        ButterKnife.bind(this, mLayout);
        initData();
        setupUI();
        return mLayout;
    }

    private void setupUI() {
        mRefresh.setOnRefreshListener(this);
    }

    private void initData() {
        mList = new ArrayList<>();

        mLayoutManager = new LinearLayoutManager(getContext());
        mRvComplete.setLayoutManager(mLayoutManager);

        mAdapter = new CompleteAdapter(CompleteFragment.this);
        mAdapter.setList(mList);
        mRvComplete.setAdapter(mAdapter);

        mPresenter = new Presenter();
        mPresenter.setView(CompleteFragment.this);
        mPresenter.getCompleteList();
    }


    public void addToCompleteList(BookingResp booking) {
        mList.add(0, booking);
        mAdapter.notifyItemInserted(0);
        mLayoutManager.scrollToPositionWithOffset(0, 0);
    }


    @Override
    public void onRefresh() {
        mRefresh.setRefreshing(false);
        if (mPresenter != null) {
            mPresenter.getCompleteList();
        }
    }

    @Override
    public void onLoading() {
        mLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void getCompleteListSuccess(BookingList list) {
        mList = list.getList();
        mLoading.setVisibility(View.GONE);
        mAdapter.setList(list.getList());
    }

    @Override
    public void getCompleteListFailed(String msg) {
        mLoading.setVisibility(View.GONE);
        Utilities.showToast(getContext(), msg);
    }
}
