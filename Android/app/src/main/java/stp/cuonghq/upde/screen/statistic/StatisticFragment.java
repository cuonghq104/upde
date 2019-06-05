package stp.cuonghq.upde.screen.statistic;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.BaseFragment;
import stp.cuonghq.upde.commons.Utilities;
import stp.cuonghq.upde.data.models.BookingList;
import stp.cuonghq.upde.data.models.BookingResp;
import stp.cuonghq.upde.data.models.StatisticGetAllPriceResponce;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticFragment extends BaseFragment<StatisticFragment, Presenter> implements Contract.View {

    private static final String TAG = StatisticFragment.class.toString();

    View mLayout;

    ArrayList<String> mListDate;
    List<BookingResp> mList;
    ArrayAdapter<String> mAdapterDate;

    CollapsingToolbarLayout mCollapsingToolbarLayout;
    AppBarLayout mAppBarLayout;

    StatisticAdapter mStatisticAdapter;
    RecyclerView mylistview;
    Toolbar toolbar;
    Spinner sp_date;
    TextView mTvTotalIncome, mTvDate;

    public StatisticFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        super.onCreateView(inflater, container, savedInstanceState);
        mLayout = inflater.inflate(R.layout.fragment_statistic, container, false);

        initView();
        setupUI();
        return mLayout;
    }

    private void onScrollDown(){
        mTvTotalIncome.setVisibility(View.VISIBLE);
        mTvDate.setVisibility(View.VISIBLE);
    }

    private void onScrollOnTheTop(){
        mTvTotalIncome.setVisibility(View.GONE);
        mTvDate.setVisibility(View.GONE);
    }

    private void setupUI() {
        onScrollDown();
        sp_date.setVisibility(View.VISIBLE);
        setDateText(6, 13, 3);
        setTotalIncomeText("3.4000.000", "vnd");
    }

    @Override
    protected Presenter initPresenter() {
        return new Presenter();
    }

    private void initView() {
        mCollapsingToolbarLayout = mLayout.findViewById(R.id.collapsing_toolbar);
        mylistview = mLayout.findViewById(R.id.mylistview);
        toolbar = mLayout.findViewById(R.id.toolbar);
        mAppBarLayout = mLayout.findViewById(R.id.appbar);
        sp_date = toolbar.findViewById(R.id.sp_date);
        mTvDate = toolbar.findViewById(R.id.tv_date);
        mTvTotalIncome = toolbar.findViewById(R.id.tv_total_income);

        initData();
        prepareDate();

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (appBarLayout.getTotalScrollRange() - (verticalOffset * -1) <= 100) {
                    onScrollDown();
                } else if (appBarLayout.getTotalScrollRange() - (verticalOffset * -1) > 100) {
                    onScrollOnTheTop();
                } else {
                    onScrollOnTheTop();
                }
            }
        });
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setToolbarText("");
    }

    private void prepareDate() {
        mListDate = new ArrayList<>();
        mListDate.add("Ngày");
        mListDate.add("Tuần");
        mListDate.add("Tháng");
        mListDate.add("Năm");
        mListDate.add("Tự chọn");

        mAdapterDate = new ArrayAdapter<>(StatisticFragment.this.getContext(), android.R.layout.simple_list_item_1, mListDate);
        sp_date.setAdapter(mAdapterDate);
    }

    private void setTotalIncomeText(String income, String unit) {
        mTvTotalIncome.setText((income != null) ? Utilities.emphasize(getContext(), income) : "");
        mTvTotalIncome.append(" " + unit);
    }

    private void setDateText(int dayOfWeeks, int date, int month) {
        /**
         * 7: Sunday
         * < 7: ... days
         */
        if (dayOfWeeks == 6) {
            mTvDate.setText(Utilities.emphasize(getContext(), "Chủ nhật"));
        } else {
            mTvDate.setText("Thứ");
            mTvDate.setText(Utilities.emphasize(getContext(), " " + (dayOfWeeks + 2)));
        }
        mTvDate.append(", ngày ");
        mTvDate.append(Utilities.emphasize(getContext(), " " + date));
        mTvDate.append(", tháng ");
        mTvDate.append(Utilities.emphasize(getContext(), " " + month));
    }

    private void initData() {
        mList = new ArrayList<BookingResp>();
        mStatisticAdapter = new StatisticAdapter(StatisticFragment.this);

        mList.add(new BookingResp());
        mStatisticAdapter.setList(mList);

        presenter.getStatistic("year",0);
        //presenter.getCompleteList("time_begin","time_end",999999999);

        mylistview.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager
                .VERTICAL, false));
        mylistview.setHasFixedSize(true);
        mStatisticAdapter = new StatisticAdapter(this);
        mylistview.setAdapter(mStatisticAdapter);
    }

    private void setToolbarText(String input) {
        toolbar.setTitle(input);
    }

    @Override
    public void getCompleteListSuccess(BookingList list) {
        mList = list.getList();
        mStatisticAdapter.setList(mList);
    }

    @Override
    public void getCompleteListFailed(String msg) {
        Utilities.showToast(getContext(), msg);
    }

    @Override
    public void getStatisticSuccess(StatisticGetAllPriceResponce list) {
        Log.d("hehe","StatisticGetAllPriceResponce "+list);
    }

    @Override
    public void getStatisticFailed(String msg) {
        Utilities.showToast(getContext(), msg);
    }
}
