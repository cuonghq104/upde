package stp.cuonghq.upde.screen.statistic;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;

import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.BaseContract;
import stp.cuonghq.upde.commons.BaseFragment;
import stp.cuonghq.upde.commons.Utilities;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticFragment extends BaseFragment<StatisticFragment, Presenter> implements Contract.View {

    private static final String TAG = StatisticFragment.class.toString();

    View mLayout;

    ArrayList<String> mList, mListDate;
    ArrayAdapter<String> mAdapterDate;

    CollapsingToolbarLayout mCollapsingToolbarLayout;
    AppBarLayout mAppBarLayout;

    StatisticAdapter mStatisticAdapter;
    RecyclerView mylistview;
    Toolbar toolbar;
    Spinner sp_date;
    ConstraintLayout mClToolbarInfo;
    RelativeLayout mRlSpinner;
    AppCompatTextView mTvTotalIncome, mTvDate;

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

    private void setupUI() {
        mClToolbarInfo.setVisibility(View.GONE);
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
        sp_date = mLayout.findViewById(R.id.sp_date);
        mClToolbarInfo = mLayout.findViewById(R.id.cl_toolbar_info);
        mTvDate = mLayout.findViewById(R.id.tv_date);
        mTvTotalIncome = mLayout.findViewById(R.id.tv_total_income);
        mRlSpinner = mLayout.findViewById(R.id.rl_spinner);

        fakeData();
        prepareDate();

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.e("hehe", "" + verticalOffset + " " + appBarLayout.getTotalScrollRange());
//                if (verticalOffset * -1 == appBarLayout.getTotalScrollRange()) {
                if (verticalOffset * -1 >= appBarLayout.getTotalScrollRange() - 100) {
                    //setToolbarText("Tong thu nhap");
                    mClToolbarInfo.setVisibility(View.VISIBLE);
                } else if (verticalOffset >= -100) {
                    mRlSpinner.setVisibility(View.VISIBLE);
                } else {
                    mRlSpinner.setVisibility(View.GONE);
                    mClToolbarInfo.setVisibility(View.GONE);
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
    private void fakeData() {
        mList = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {

            mList.add("item " + i);
        }

        //mAdater = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, mList);
        mylistview.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager
                .VERTICAL, false));
        mylistview.setHasFixedSize(true);
        mStatisticAdapter = new StatisticAdapter(mList);
        mylistview.setAdapter(mStatisticAdapter);
    }

    private void setToolbarText(String input) {
        toolbar.setTitle(input);
    }
}
