package stp.cuonghq.upde.screen.statistic.host;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.listener.OnDrawListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.AppSharePreferences;
import stp.cuonghq.upde.commons.BaseFragment;
import stp.cuonghq.upde.commons.Constants;
import stp.cuonghq.upde.commons.EndlessRecyclerOnScrollListener;
import stp.cuonghq.upde.commons.Utilities;
import stp.cuonghq.upde.data.models.BookingList;
import stp.cuonghq.upde.data.models.BookingResp;
import stp.cuonghq.upde.data.models.DataStatisticDateTime;
import stp.cuonghq.upde.data.models.DataStatisticTime;
import stp.cuonghq.upde.data.models.StatisticGetAllPriceResponce;
import stp.cuonghq.upde.data.models.dbentities.DayStatisticEntity;
import stp.cuonghq.upde.data.models.dbentities.MonthStatisticEntity;
import stp.cuonghq.upde.data.models.dbentities.WeekStatisticEntity;
import stp.cuonghq.upde.data.models.dbentities.YearStatisticEntity;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticFragment extends BaseFragment<StatisticFragment, Presenter> implements Contract.View {

    private static final String TAG = StatisticFragment.class.toString();

    View mLayout;
    ArrayList<DataStatisticDateTime> mListDataStatisticTime;
    ArrayList<String> mListDate, mListDateVn;
    List<BookingResp> mList;
    ArrayAdapter<String> mAdapterDate;
    ArrayList<IBarDataSet> arr;
    ArrayList tempList, updateList;
    BarDataSet bardataset;
    BarData data;

    CollapsingToolbarLayout mCollapsingToolbarLayout;
    AppBarLayout mAppBarLayout;

    StatisticAdapter mStatisticAdapter;
    RecyclerView mylistview;
    Toolbar toolbar;
    Spinner sp_date;
    TextView mTvTotalIncome, mTvDate, tv_linearlayout_detail_price_number, tv_linearlayout_detail_date, tv_no_list_trip, tv_linearlayout_number_book_quality;

    BarChart chart;
    ProgressBar mLoading, mloadingChart;

    static String mStartDate, mEndDate;
    static String selectMode = "day";
    static int selectNumber = 0;
    static long VERY_LONG_NUMBER = 999999999;
    static int THRESH_HOLD_LEFT = 5;
    static int MAX_TIME_DISPLAY = 7;
    static int PAGE_NUMBER = 0;
    static int currentPosition;
    static float LOWEST_X = -0.5f;
    static boolean mCheckRefresh = false;
    static int MAX_ITEM_PER_PAGE = 14;

    EndlessRecyclerOnScrollListener onScrollListener;

    public StatisticFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        super.onCreateView(inflater, container, savedInstanceState);
        mLayout = inflater.inflate(R.layout.fragment_host_statistic, container, false);

        initView();
        setupUI();
        return mLayout;
    }

    private void onScrollDown() {
        mTvTotalIncome.setVisibility(View.VISIBLE);
        mTvDate.setVisibility(View.VISIBLE);
    }

    private void onScrollOnTheTop() {
        mTvTotalIncome.setVisibility(View.GONE);
        mTvDate.setVisibility(View.GONE);
    }

    private void setupUI() {
        onScrollDown();
        sp_date.setVisibility(View.VISIBLE);
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
        mLoading = mLayout.findViewById(R.id.layout_loading_statistic);
        mloadingChart = mLayout.findViewById(R.id.layout_loading_statistic_chart);
        tv_linearlayout_detail_price_number = mLayout.findViewById(R.id.tv_linearlayout_detail_price_number);
        tv_linearlayout_detail_date = mLayout.findViewById(R.id.tv_linearlayout_detail_date);
        tv_linearlayout_number_book_quality = mLayout.findViewById(R.id.tv_linearlayout_number_book_quality);
        mLoading.setVisibility(View.VISIBLE);
        mloadingChart.setVisibility(View.VISIBLE);
        chart = mLayout.findViewById(R.id.chart1);
        tv_no_list_trip = mLayout.findViewById(R.id.tv_no_list_trip);
        tv_no_list_trip.setVisibility(View.GONE);

        mListDataStatisticTime = new ArrayList<>();

        mListDataStatisticTime.add(new DataStatisticDateTime(Constants.DateAndTime.TYPE_DAY));
        mListDataStatisticTime.add(new DataStatisticDateTime(Constants.DateAndTime.TYPE_WEEK));
        mListDataStatisticTime.add(new DataStatisticDateTime(Constants.DateAndTime.TYPE_MONTH));
        mListDataStatisticTime.add(new DataStatisticDateTime(Constants.DateAndTime.TYPE_YEAR));

        chart.setNoDataText(getActivity().getApplicationContext().getString(R.string.no_description_text));
        chart.setPinchZoom(false);

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (selectMode.equals(Constants.DateAndTime.TYPE_DAY)) {
                    mStartDate = Utilities.dateToStringStatisticStartDate(selectMode, mListDataStatisticTime.get(0).getmListDataStatistic().get((int) e.getX()).getTime());
                    mStartDate = Utilities.dateToStringStatisticStartDate(selectMode, mListDataStatisticTime.get(0).getmListDataStatistic().get((int) e.getX()).getTime());
                    setDateText(mListDataStatisticTime.get(0).getmListDataStatistic().get((int) e.getX()).getPrice().toString(), mListDataStatisticTime.get(0).getmListDataStatistic().get((int) e.getX()).getNumberTrip(), mListDataStatisticTime.get(0).getmListDataStatistic().get((int) e.getX()).getTime().toString());

                } else if (selectMode.equals(Constants.DateAndTime.TYPE_WEEK)) {
                    mStartDate = Utilities.dateToStringStatisticStartDate(selectMode, mListDataStatisticTime.get(1).getmListDataStatistic().get((int) e.getX()).getTime());
                    mStartDate = Utilities.dateToStringStatisticStartDate(selectMode, mListDataStatisticTime.get(1).getmListDataStatistic().get((int) e.getX()).getTime());
                    setDateText(mListDataStatisticTime.get(1).getmListDataStatistic().get((int) e.getX()).getPrice().toString(), mListDataStatisticTime.get(1).getmListDataStatistic().get((int) e.getX()).getNumberTrip(), mListDataStatisticTime.get(1).getmListDataStatistic().get((int) e.getX()).getTime().toString());

                } else if (selectMode.equals(Constants.DateAndTime.TYPE_MONTH)) {
                    mStartDate = Utilities.dateToStringStatisticStartDate(selectMode, mListDataStatisticTime.get(2).getmListDataStatistic().get((int) e.getX()).getTime());
                    mStartDate = Utilities.dateToStringStatisticStartDate(selectMode, mListDataStatisticTime.get(2).getmListDataStatistic().get((int) e.getX()).getTime());
                    setDateText(mListDataStatisticTime.get(2).getmListDataStatistic().get((int) e.getX()).getPrice().toString(), mListDataStatisticTime.get(2).getmListDataStatistic().get((int) e.getX()).getNumberTrip(), mListDataStatisticTime.get(2).getmListDataStatistic().get((int) e.getX()).getTime().toString());

                } else if (selectMode.equals(Constants.DateAndTime.TYPE_YEAR)) {
                    mStartDate = Utilities.dateToStringStatisticStartDate(selectMode, mListDataStatisticTime.get(3).getmListDataStatistic().get((int) e.getX()).getTime());
                    mStartDate = Utilities.dateToStringStatisticStartDate(selectMode, mListDataStatisticTime.get(3).getmListDataStatistic().get((int) e.getX()).getTime());
                    setDateText(mListDataStatisticTime.get(3).getmListDataStatistic().get((int) e.getX()).getPrice().toString(), mListDataStatisticTime.get(3).getmListDataStatistic().get((int) e.getX()).getNumberTrip(), mListDataStatisticTime.get(3).getmListDataStatistic().get((int) e.getX()).getTime().toString());

                }
                presenter.getCompleteList(mStartDate, mEndDate, VERY_LONG_NUMBER);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        chart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                mCheckRefresh = false;
            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
            }

            @Override
            public void onChartLongPressed(MotionEvent me) {
            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {
            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {
                if (!mCheckRefresh) {
                    if (chart.getLowestVisibleX() <= LOWEST_X && dX > THRESH_HOLD_LEFT) {
                        Log.d("hehe", "onSwipeRight");
                        PAGE_NUMBER++;
                        if (checkUpdate()) {
                            loading();
                            presenter.getStatistic(selectMode, PAGE_NUMBER);
                        }
                    }
                    mCheckRefresh = true;
                }
            }
        });

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
        mListDate.add(getActivity().getApplicationContext().getString(R.string.day));
        mListDate.add(getActivity().getApplicationContext().getString(R.string.week));
        mListDate.add(getActivity().getApplicationContext().getString(R.string.month));
        mListDate.add(getActivity().getApplicationContext().getString(R.string.year));

        mListDateVn = new ArrayList<>();
        mListDateVn.add("day");
        mListDateVn.add("week");
        mListDateVn.add("month");
        mListDateVn.add("year");
        mAdapterDate = new ArrayAdapter<>(StatisticFragment.this.getContext(), android.R.layout.simple_list_item_1, mListDate);
        sp_date.setAdapter(mAdapterDate);
        sp_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectMode = mListDateVn.get(position);
                resetData();
                Log.e("hehe", "" + checkUpdate());
                if (checkUpdate()) {
                    loading();
                    presenter.getStatistic(selectMode, PAGE_NUMBER);
                } else {
                    setBarCharData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setTotalIncomeText(String income, String unit) {
        mTvTotalIncome.setText((income != null) ? Utilities.emphasize(getContext(), income) : "");
        mTvTotalIncome.append(" " + unit);
    }

    private boolean checkUpdate() {
        if (selectMode.equals(Constants.DateAndTime.TYPE_DAY)) {
            if (mListDataStatisticTime.get(0).getmPageNumber() < PAGE_NUMBER) {
                return true;
            }
        } else if (selectMode.equals(Constants.DateAndTime.TYPE_WEEK)) {
            if (mListDataStatisticTime.get(1).getmPageNumber() < PAGE_NUMBER) {
                return true;
            }
        } else if (selectMode.equals(Constants.DateAndTime.TYPE_MONTH)) {
            if (mListDataStatisticTime.get(2).getmPageNumber() < PAGE_NUMBER) {
                return true;
            }
        } else if (selectMode.equals(Constants.DateAndTime.TYPE_YEAR)) {
            if (mListDataStatisticTime.get(3).getmPageNumber() < PAGE_NUMBER) {
                return true;
            }
        }
        return false;
    }

    private void setDateText(String price, int numberTrip, String key) {
        String[] mTempStartDate = mStartDate.split(" ");
        String[] mTempEndDate = mEndDate.split(" ");
        if (selectMode.equals(Constants.DateAndTime.TYPE_DAY)) {
            String[] mTempKey = key.split("/");
            String tempDayOFWeek = Utilities.getDayOfWeek(key);
            mTvDate.setText(tempDayOFWeek + ",ngày " + mTempKey[1] + " tháng " + mTempKey[0]);
            tv_linearlayout_detail_date.setText(tempDayOFWeek + ",ngày " + mTempKey[1] + " tháng " + mTempKey[0]);

        } else if (selectMode.equals(Constants.DateAndTime.TYPE_MONTH)) {
            String[] mTempKey = key.split("/");
            mTvDate.setText("Tháng " + mTempKey[0] + ",năm " + mTempKey[1]);
            tv_linearlayout_detail_date.setText("Tháng " + mTempKey[0] + ",năm " + mTempKey[1]);
        } else if (selectMode.equals(Constants.DateAndTime.TYPE_YEAR)) {
            mTvDate.setText("Năm " + key);
            tv_linearlayout_detail_date.setText("Năm " + key);
        } else if (selectMode.equals(Constants.DateAndTime.TYPE_WEEK)) {
            String[] mTempKey = key.split(" ");
            String[] mDetailStartDate = mTempStartDate[0].split("/");
            String[] mDetailEndDate = mTempEndDate[0].split("/");
            mTvDate.setText("Tuần " + mTempKey[0].substring(1) + ", " + mDetailStartDate[1] + " tháng " + mDetailStartDate[0] + " - " + mDetailEndDate[1] + " tháng " + mDetailEndDate[0]);
            tv_linearlayout_detail_date.setText("Tuần " + mTempKey[0].substring(1) + ", " + mDetailStartDate[1] + " tháng " + mDetailStartDate[0] + " - " + mDetailEndDate[1] + " tháng " + mDetailEndDate[0]);
        }

        tv_linearlayout_detail_price_number.setText(Utilities.changeDollarToVietNam(price));
        mTvTotalIncome.setText(Utilities.changeDollarToVietNam(price));
        tv_linearlayout_number_book_quality.setText(numberTrip + "");
    }

    private void initData() {
        mList = new ArrayList<BookingResp>();
        mStatisticAdapter = new StatisticAdapter(StatisticFragment.this);

        mList.add(new BookingResp());
        mStatisticAdapter.setList(mList);

        mylistview.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager
                .VERTICAL, false));
        mylistview.setHasFixedSize(true);
        mStatisticAdapter = new StatisticAdapter(this);
        mylistview.setAdapter(mStatisticAdapter);

        onScrollListener = new EndlessRecyclerOnScrollListener(
                new EndlessRecyclerOnScrollListener.LoadMoreCallback() {
                    @Override
                    public void loadMore() {
                        presenter.getCompleteList(mStartDate, mEndDate, VERY_LONG_NUMBER--);
                    }
                }
        );
        mylistview.addOnScrollListener(onScrollListener);
        resetData();
    }

    private void setToolbarText(String input) {
        toolbar.setTitle(input);
    }

    @Override
    public void getCompleteListSuccess(BookingList list) {
        onScrollListener.setLoadingStatus(false);
        mList = list.getList();
        mStatisticAdapter.setList(mList);
        mLoading.setVisibility(View.GONE);
        if (mList.size() == 0) {
            mylistview.setVisibility(View.GONE);
            tv_no_list_trip.setVisibility(View.VISIBLE);
        } else {
            mylistview.setVisibility(View.VISIBLE);
            tv_no_list_trip.setVisibility(View.GONE);
        }
    }

    @Override
    public void getCompleteListFailed(String msg) {
        onScrollListener.setLoadingStatus(false);
        Utilities.showToast(getContext(), msg);
        mLoading.setVisibility(View.GONE);
        if (mList.size() == 0) {
            mylistview.setVisibility(View.GONE);
            tv_no_list_trip.setVisibility(View.VISIBLE);
        } else {
            mylistview.setVisibility(View.VISIBLE);
            tv_no_list_trip.setVisibility(View.GONE);
        }
        VERY_LONG_NUMBER++;
    }

    @Override
    public void getStatisticSuccess(StatisticGetAllPriceResponce list) {
        Log.d("hehe", "responce " + list.getData().toString() + " count  " + list.getData().size());

        loaded();

//        boolean ttB = AppSharePreferences.getInstance().getBoolean(Constants.SharePreferenceConstants.STATISTIC, false);
        boolean insertNormal = true;

        //điều chỉnh xem có cần update database hay không , nếu cần thì update page number của các trường (cách nhau 13)
        if (PAGE_NUMBER == 0) {
            Log.e("hehe", "PAGE_NUMBER 0 ");
            if (selectMode.equals(Constants.DateAndTime.TYPE_DAY)) {
                DayStatisticEntity tt = presenter.getCurrent(selectMode);
                Log.e("hehe", "tt " + tt);
                if (tt != null) {
                    insertNormal = false;
                    if (Utilities.needUpdateDatabase(selectMode, System.currentTimeMillis(), tt.getDay())) {
                        Log.d("hehe", "data is too old --> update ");
                        int totalTemp = presenter.getMaxBy(selectMode);
                        updateList = new ArrayList<DayStatisticEntity>(presenter.getAll(selectMode));

                        for (int i = 13; i < updateList.size(); i += MAX_ITEM_PER_PAGE) {
                            DayStatisticEntity tta = (DayStatisticEntity) updateList.get(i);
                            Log.d("hehe", "change " + tta);
                            tta.setPageNumber(tta.getPageNumber() + 1);
                            Log.d("hehe", "after change " + tta);
                            updateList.set(i, tta);
                        }

                        //delete từ database
                        presenter.delete(selectMode);

                        updateList.add(0, new DayStatisticEntity(list.getData().get(0).getTime(), String.valueOf(list.getData().get(0).getPrice()), String.valueOf(list.getData().get(0).getNumberTrip()), PAGE_NUMBER));

                        //xóa đi phần tử cối cùng,vì phần tử này sẽ get từ serve
                        updateList.remove(updateList.size() - 1);
                    } else {
                        // needupdate = false;
                        tt.setNumbertrip(String.valueOf(list.getData().get(0).getNumberTrip()));
                        tt.setPrice(String.valueOf(list.getData().get(0).getPrice()));
                        presenter.update(tt.getDay(), tt.getPrice(), tt.getNumbertrip(), selectMode, tt.getPageNumber());
                    }
                } else {
                    insertNormal = true;
                }
            } else if (selectMode.equals(Constants.DateAndTime.TYPE_WEEK)) {
                WeekStatisticEntity tt = presenter.getCurrent(selectMode);
                if (tt != null) {
                    insertNormal = false;
                    if (Utilities.needUpdateDatabase(selectMode, System.currentTimeMillis(), tt.getTime())) {
                        Log.d("hehe", "data is too old --> update ");
                        int totalTemp = presenter.getMaxBy(selectMode);
                        updateList = new ArrayList<WeekStatisticEntity>(presenter.getAll(selectMode));

                        for (int i = 13; i < updateList.size(); i += MAX_ITEM_PER_PAGE) {
                            WeekStatisticEntity tta = (WeekStatisticEntity) updateList.get(i);
                            Log.d("hehe", "change " + tta);
                            tta.setPageNumber(tta.getPageNumber() + 1);
                            Log.d("hehe", "after change " + tta);
                            updateList.set(i, tta);
                        }

                        //delete từ database
                        presenter.delete(selectMode);

                        updateList.add(0, new WeekStatisticEntity(list.getData().get(0).getTime(), String.valueOf(list.getData().get(0).getPrice()), String.valueOf(list.getData().get(0).getNumberTrip()), PAGE_NUMBER));

                        //xóa đi phần tử cối cùng,vì phần tử này sẽ get từ serve
                        updateList.remove(updateList.size() - 1);
                    } else {
                        // needupdate = false;
                        tt.setNumbertrip(String.valueOf(list.getData().get(0).getNumberTrip()));
                        tt.setPrice(String.valueOf(list.getData().get(0).getPrice()));
                        presenter.update(tt.getTime(), tt.getPrice(), tt.getNumbertrip(), selectMode, tt.getPageNumber());
                    }
                } else {
                    insertNormal = true;
                }

            } else if (selectMode.equals(Constants.DateAndTime.TYPE_MONTH)) {
                MonthStatisticEntity tt = presenter.getCurrent(selectMode);
                if (tt != null) {
                    insertNormal = false;
                    if (Utilities.needUpdateDatabase(selectMode, System.currentTimeMillis(), tt.getTime())) {
                        updateList = new ArrayList<MonthStatisticEntity>(presenter.getAll(selectMode));
                        updateList = new ArrayList<MonthStatisticEntity>(presenter.getAll(selectMode));

                        Log.d("hehe", "data is too old --> update ");
                        int totalTemp = presenter.getMaxBy(selectMode);

                        for (int i = 13; i < updateList.size(); i += MAX_ITEM_PER_PAGE) {
                            MonthStatisticEntity tta = (MonthStatisticEntity) updateList.get(i);
                            Log.d("hehe", "change " + tta);
                            tta.setPageNumber(tta.getPageNumber() + 1);
                            Log.d("hehe", "after change " + tta);
                            updateList.set(i, tta);
                        }


                        //delete từ database
                        presenter.delete(selectMode);

                        updateList.add(0, new MonthStatisticEntity(list.getData().get(0).getTime(), String.valueOf(list.getData().get(0).getPrice()), String.valueOf(list.getData().get(0).getNumberTrip()), PAGE_NUMBER));

                        //xóa đi phần tử cối cùng,vì phần tử này sẽ get từ serve
                        updateList.remove(updateList.size() - 1);
                    } else {
                        // needupdate = false;
                        Log.d("hehe", "data is not old but current day need update price and number trip --> update ");
                        tt.setNumbertrip(String.valueOf(list.getData().get(0).getNumberTrip()));
                        tt.setPrice(String.valueOf(list.getData().get(0).getPrice()));
                        presenter.update(tt.getTime(), tt.getPrice(), tt.getNumbertrip(), selectMode, tt.getPageNumber());
                    }
                } else {
                    insertNormal = true;
                }

            } else if (selectMode.equals(Constants.DateAndTime.TYPE_YEAR)) {
                YearStatisticEntity tt = presenter.getCurrent(selectMode);
                if (tt != null) {
                    insertNormal = false;
                    if (Utilities.needUpdateDatabase(selectMode, System.currentTimeMillis(), tt.getTime())) {
                        Log.d("hehe", "data is too old --> update ");
                        int totalTemp = presenter.getMaxBy(selectMode);
                        updateList = new ArrayList<YearStatisticEntity>(presenter.getAll(selectMode));
                        for (int i = 13; i < updateList.size(); i += MAX_ITEM_PER_PAGE) {
                            YearStatisticEntity tta = (YearStatisticEntity) updateList.get(i);
                            Log.d("hehe", "change " + tta);
                            tta.setPageNumber(tta.getPageNumber() + 1);
                            Log.d("hehe", "after change " + tta);
                            updateList.set(i, tta);
                        }


                        //delete từ database
                        presenter.delete(selectMode);

                        updateList.add(0, new YearStatisticEntity(list.getData().get(0).getTime(), String.valueOf(list.getData().get(0).getPrice()), String.valueOf(list.getData().get(0).getNumberTrip()), PAGE_NUMBER));


                        //xóa đi phần tử cối cùng,vì phần tử này sẽ get từ serve
                        updateList.remove(updateList.size() - 1);
                    } else {
                        Log.d("hehe", "data is not old but current day need update price and number trip --> update ");
                        // needupdate = false;
                        tt.setNumbertrip(String.valueOf(list.getData().get(0).getNumberTrip()));
                        tt.setPrice(String.valueOf(list.getData().get(0).getPrice()));
                        presenter.update(tt.getTime(), tt.getPrice(), tt.getNumbertrip(), selectMode, tt.getPageNumber());

                    }
                } else {
                    insertNormal = true;
                }
            }

            //update vào sharepreference
            // AppSharePreferences.saveToSP(Constants.SharePreferenceConstants.STATISTIC, true);

            if (updateList != null) {
                //tempList = new ArrayList(updateList);
                selectNumber += updateList.size();

                //upload all vào database
                //bỏ đi cái cuối
                for (int i = 0; i <= updateList.size() - 1; i++) {
                    if (selectMode.equals(Constants.DateAndTime.TYPE_DAY)) {
                        DayStatisticEntity tta = (DayStatisticEntity) updateList.get(i);
                        presenter.insert(tta.getDay(), String.valueOf(tta.getPrice()),
                                String.valueOf(tta.getNumbertrip()), Constants.DateAndTime.TYPE_DAY, tta.getPageNumber());
                    } else if (selectMode.equals(Constants.DateAndTime.TYPE_WEEK)) {
                        WeekStatisticEntity tta = (WeekStatisticEntity) updateList.get(i);
                        presenter.insert(tta.getTime(), String.valueOf(tta.getPrice()),
                                String.valueOf(tta.getNumbertrip()), Constants.DateAndTime.TYPE_WEEK, tta.getPageNumber());
                    } else if (selectMode.equals(Constants.DateAndTime.TYPE_MONTH)) {
                        MonthStatisticEntity tta = (MonthStatisticEntity) updateList.get(i);
                        presenter.insert(tta.getTime(), String.valueOf(tta.getPrice()),
                                String.valueOf(tta.getNumbertrip()), Constants.DateAndTime.TYPE_MONTH, tta.getPageNumber());
                    } else if (selectMode.equals(Constants.DateAndTime.TYPE_YEAR)) {
                        YearStatisticEntity tta = (YearStatisticEntity) updateList.get(i);
                        presenter.insert(tta.getTime(), String.valueOf(tta.getPrice()),
                                String.valueOf(tta.getNumbertrip()), Constants.DateAndTime.TYPE_YEAR, tta.getPageNumber());
                    }
                }

            }
        }
        if (insertNormal) {
            tempList = new ArrayList(list.getData());
            selectNumber += list.getData().size();

            //upload vào database
            for (int i = 0; i <= tempList.size() - 1; i++) {
                if (selectMode.equals(Constants.DateAndTime.TYPE_DAY)) {
                    DataStatisticTime tta = (DataStatisticTime) tempList.get(i);
                    presenter.insert(tta.getTime(), String.valueOf(tta.getPrice()),
                            String.valueOf(tta.getNumberTrip()), Constants.DateAndTime.TYPE_DAY, PAGE_NUMBER);
                } else if (selectMode.equals(Constants.DateAndTime.TYPE_WEEK)) {
                    DataStatisticTime tta = (DataStatisticTime) tempList.get(i);
                    presenter.insert(tta.getTime(), String.valueOf(tta.getPrice()),
                            String.valueOf(tta.getNumberTrip()), Constants.DateAndTime.TYPE_WEEK, PAGE_NUMBER);
                } else if (selectMode.equals(Constants.DateAndTime.TYPE_MONTH)) {
                    DataStatisticTime tta = (DataStatisticTime) tempList.get(i);
                    presenter.insert(tta.getTime(), String.valueOf(tta.getPrice()),
                            String.valueOf(tta.getNumberTrip()), Constants.DateAndTime.TYPE_MONTH, PAGE_NUMBER);
                } else if (selectMode.equals(Constants.DateAndTime.TYPE_YEAR)) {
                    DataStatisticTime tta = (DataStatisticTime) tempList.get(i);
                    presenter.insert(tta.getTime(), String.valueOf(tta.getPrice()),
                            String.valueOf(tta.getNumberTrip()), Constants.DateAndTime.TYPE_YEAR, PAGE_NUMBER);
                }
            }

        }
        setBarCharData();

    }

    @Override
    public void getStatisticFailed(String msg) {
        mloadingChart.setVisibility(View.GONE);
        chart.setAlpha(1f);
        Utilities.showToast(getContext(), "" + msg);
        PAGE_NUMBER--;
    }

    private void moveChart(int position) {
        chart.setFitBars(true);
        chart.setVisibleXRangeMaximum(position / 2);
        chart.moveViewToX(position);
        chart.animateY(1000);
        chart.invalidate();
    }

    private void resetData() {
        PAGE_NUMBER = 0;
        VERY_LONG_NUMBER = 999999999;

        if (selectMode.equals(Constants.DateAndTime.TYPE_DAY)) {
            PAGE_NUMBER = mListDataStatisticTime.get(0).getmPageNumber() == -1 ? 0 : mListDataStatisticTime.get(0).getmPageNumber();
        } else if (selectMode.equals(Constants.DateAndTime.TYPE_WEEK)) {
            PAGE_NUMBER = mListDataStatisticTime.get(1).getmPageNumber() == -1 ? 0 : mListDataStatisticTime.get(1).getmPageNumber();
        } else if (selectMode.equals(Constants.DateAndTime.TYPE_MONTH)) {
            PAGE_NUMBER = mListDataStatisticTime.get(2).getmPageNumber() == -1 ? 0 : mListDataStatisticTime.get(2).getmPageNumber();
        } else if (selectMode.equals(Constants.DateAndTime.TYPE_YEAR)) {
            PAGE_NUMBER = mListDataStatisticTime.get(3).getmPageNumber() == -1 ? 0 : mListDataStatisticTime.get(3).getmPageNumber();
        }

        arr = new ArrayList<>();
        bardataset = new BarDataSet(new ArrayList<BarEntry>(), "Thu nhập");
    }

    private void setBarCharData() {
        //lấy data từ database ra

        PAGE_NUMBER = presenter.getMaxBy(selectMode);

        Log.d("hehe", "PAGE_NUMBER " + PAGE_NUMBER + " selectMode " + selectMode);

        if (selectMode.equals(Constants.DateAndTime.TYPE_DAY)) {
            mListDataStatisticTime.get(0).setmPageNumber(PAGE_NUMBER);
        } else if (selectMode.equals(Constants.DateAndTime.TYPE_WEEK)) {
            mListDataStatisticTime.get(1).setmPageNumber(PAGE_NUMBER);
        } else if (selectMode.equals(Constants.DateAndTime.TYPE_MONTH)) {
            mListDataStatisticTime.get(2).setmPageNumber(PAGE_NUMBER);
        } else if (selectMode.equals(Constants.DateAndTime.TYPE_YEAR)) {
            mListDataStatisticTime.get(3).setmPageNumber(PAGE_NUMBER);
        }

        if (selectMode.equals(Constants.DateAndTime.TYPE_DAY)) {
            tempList = new ArrayList<DayStatisticEntity>(presenter.getAll(selectMode));
        } else if (selectMode.equals(Constants.DateAndTime.TYPE_WEEK)) {
            tempList = new ArrayList<WeekStatisticEntity>(presenter.getAll(selectMode));
        } else if (selectMode.equals(Constants.DateAndTime.TYPE_MONTH)) {
            tempList = new ArrayList<MonthStatisticEntity>(presenter.getAll(selectMode));
        } else if (selectMode.equals(Constants.DateAndTime.TYPE_YEAR)) {
            tempList = new ArrayList<YearStatisticEntity>(presenter.getAll(selectMode));
        }


        for (int i = 0; i < tempList.size(); i++) {
            if (selectMode.equals(Constants.DateAndTime.TYPE_DAY)) {
                DayStatisticEntity a = (DayStatisticEntity) tempList.get(i);
                String[] tempString = a.getDay().split("/");
                mListDataStatisticTime.get(0).getmListKey().add(0, tempString[0] + "/" + tempString[1]);
                mListDataStatisticTime.get(0).getmListDataStatistic().add(0, new DataStatisticTime(a.getDay(), Double.parseDouble(a.getPrice()), Integer.parseInt(a.getNumbertrip())));
            } else if (selectMode.equals(Constants.DateAndTime.TYPE_WEEK)) {
                WeekStatisticEntity a = (WeekStatisticEntity) tempList.get(i);
                mListDataStatisticTime.get(1).getmListKey().add(0, a.getTime().split(" ")[0]);
                mListDataStatisticTime.get(1).getmListDataStatistic().add(0, new DataStatisticTime(a.getTime(), Double.parseDouble(a.getPrice()), Integer.parseInt(a.getNumbertrip())));
            } else if (selectMode.equals(Constants.DateAndTime.TYPE_MONTH)) {
                MonthStatisticEntity a = (MonthStatisticEntity) tempList.get(i);
                String[] tempString = a.getTime().split("/");
                mListDataStatisticTime.get(2).getmListKey().add(0, tempString[0]);
                mListDataStatisticTime.get(2).getmListDataStatistic().add(0, new DataStatisticTime(a.getTime(), Double.parseDouble(a.getPrice()), Integer.parseInt(a.getNumbertrip())));
            } else if (selectMode.equals(Constants.DateAndTime.TYPE_YEAR)) {
                YearStatisticEntity a = (YearStatisticEntity) tempList.get(i);
                mListDataStatisticTime.get(3).getmListKey().add(0, a.getTime());
                mListDataStatisticTime.get(3).getmListDataStatistic().add(0, new DataStatisticTime(a.getTime(), Double.parseDouble(a.getPrice()), Integer.parseInt(a.getNumbertrip())));
            }
        }

        Log.d("hehe", "dataaaa " + mListDataStatisticTime.get(0).getmListKey());
        Log.d("hehe", "dataaaa " + mListDataStatisticTime.get(1).getmListKey());
        Log.d("hehe", "dataaaa " + mListDataStatisticTime.get(2).getmListKey());
        Log.d("hehe", "dataaaa " + mListDataStatisticTime.get(3).getmListKey());

        //maxNumberTemp là số 13
        int maxNumberTemp = (mListDataStatisticTime.get(0).getmPageNumber() + 1) * MAX_ITEM_PER_PAGE - 1;

        if (selectMode.equals(Constants.DateAndTime.TYPE_DAY)) {
            maxNumberTemp = (mListDataStatisticTime.get(0).getmPageNumber() + 1) * MAX_ITEM_PER_PAGE - 1;
        } else if (selectMode.equals(Constants.DateAndTime.TYPE_WEEK)) {
            maxNumberTemp = (mListDataStatisticTime.get(1).getmPageNumber() + 1) * MAX_ITEM_PER_PAGE - 1;
        } else if (selectMode.equals(Constants.DateAndTime.TYPE_MONTH)) {
            maxNumberTemp = (mListDataStatisticTime.get(2).getmPageNumber() + 1) * MAX_ITEM_PER_PAGE - 1;
        } else if (selectMode.equals(Constants.DateAndTime.TYPE_YEAR)) {
            maxNumberTemp = (mListDataStatisticTime.get(3).getmPageNumber() + 1) * MAX_ITEM_PER_PAGE - 1;
        }

        Log.d("hehe", "size " + maxNumberTemp);

        //xét giá trị cho các cột
        for (int i = 0; i <= maxNumberTemp; i++) {
            if (selectMode.equals(Constants.DateAndTime.TYPE_DAY)) {
                bardataset.addEntry(new BarEntry(i, Float.parseFloat(String.valueOf(mListDataStatisticTime.get(0).getmListDataStatistic().get(i).getPrice()))));
            } else if (selectMode.equals(Constants.DateAndTime.TYPE_WEEK)) {
                bardataset.addEntry(new BarEntry(i, Float.parseFloat(String.valueOf(mListDataStatisticTime.get(1).getmListDataStatistic().get(i).getPrice()))));
            } else if (selectMode.equals(Constants.DateAndTime.TYPE_MONTH)) {
                bardataset.addEntry(new BarEntry(i, Float.parseFloat(String.valueOf(mListDataStatisticTime.get(2).getmListDataStatistic().get(i).getPrice()))));
            } else if (selectMode.equals(Constants.DateAndTime.TYPE_YEAR)) {
                bardataset.addEntry(new BarEntry(i, Float.parseFloat(String.valueOf(mListDataStatisticTime.get(3).getmListDataStatistic().get(i).getPrice()))));
            }
        }
        Log.d("hehe", "bar data set " + bardataset.getValues());
        Log.d("hehe", "bar data set count " + bardataset.getEntryCount());
        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(MAX_TIME_DISPLAY);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //set giá trị cho trục hoành
        if (selectMode.equals(Constants.DateAndTime.TYPE_DAY)) {
            xAxis.setValueFormatter(new IndexAxisValueFormatter(mListDataStatisticTime.get(0).getmListKey()));
        } else if (selectMode.equals(Constants.DateAndTime.TYPE_WEEK)) {
            xAxis.setValueFormatter(new IndexAxisValueFormatter(mListDataStatisticTime.get(1).getmListKey()));
        } else if (selectMode.equals(Constants.DateAndTime.TYPE_MONTH)) {
            xAxis.setValueFormatter(new IndexAxisValueFormatter(mListDataStatisticTime.get(2).getmListKey()));
        } else if (selectMode.equals(Constants.DateAndTime.TYPE_YEAR)) {
            xAxis.setValueFormatter(new IndexAxisValueFormatter(mListDataStatisticTime.get(3).getmListKey()));
        }


        YAxis right = chart.getAxisRight();
        right.setDrawLabels(true);
        right.setDrawAxisLine(false);
        right.setDrawGridLines(true);
        right.setDrawZeroLine(true);
        right.setLabelCount(MAX_TIME_DISPLAY);
        chart.getAxisLeft().setEnabled(false);

        arr = new ArrayList<>();
        arr.add(bardataset);
        data = new BarData(arr);
        data.setBarWidth(0.6f);
        data.setDrawValues(true);

        chart.setData(data);
        chart.notifyDataSetChanged();
        chart.invalidate();
        chart.animateY(1000);
        chart.setMaxVisibleValueCount(selectNumber + 1);
        chart.setDrawValueAboveBar(true);

        currentPosition = selectNumber - (selectNumber + 1) / 2 + 1;

        //lấy giá trị thứ 13 set cho text
        //và di chuyển chart đến phần tử thứ 13(vì nhé data vào đầu)
        if (selectMode.equals(Constants.DateAndTime.TYPE_DAY)) {
            mStartDate = Utilities.dateToStringStatisticStartDate(selectMode, String.valueOf(mListDataStatisticTime.get(0).getmListDataStatistic().get(maxNumberTemp).getTime()));
            mEndDate = Utilities.dateToStringStatisticEndDate(selectMode, String.valueOf(mListDataStatisticTime.get(0).getmListDataStatistic().get(maxNumberTemp).getTime()));
            setDateText(String.valueOf(mListDataStatisticTime.get(0).getmListDataStatistic().get(maxNumberTemp).getPrice()), mListDataStatisticTime.get(0).getmListDataStatistic().get(maxNumberTemp).getNumberTrip(), mListDataStatisticTime.get(0).getmListDataStatistic().get(maxNumberTemp).getTime());
        } else if (selectMode.equals(Constants.DateAndTime.TYPE_WEEK)) {
            mStartDate = Utilities.dateToStringStatisticStartDate(selectMode, String.valueOf(mListDataStatisticTime.get(1).getmListDataStatistic().get(maxNumberTemp).getTime()));
            mEndDate = Utilities.dateToStringStatisticEndDate(selectMode, String.valueOf(mListDataStatisticTime.get(1).getmListDataStatistic().get(maxNumberTemp).getTime()));
            setDateText(String.valueOf(mListDataStatisticTime.get(1).getmListDataStatistic().get(maxNumberTemp).getPrice()), mListDataStatisticTime.get(1).getmListDataStatistic().get(maxNumberTemp).getNumberTrip(), mListDataStatisticTime.get(1).getmListDataStatistic().get(maxNumberTemp).getTime());
        } else if (selectMode.equals(Constants.DateAndTime.TYPE_MONTH)) {
            mStartDate = Utilities.dateToStringStatisticStartDate(selectMode, String.valueOf(mListDataStatisticTime.get(2).getmListDataStatistic().get(maxNumberTemp).getTime()));
            mEndDate = Utilities.dateToStringStatisticEndDate(selectMode, String.valueOf(mListDataStatisticTime.get(2).getmListDataStatistic().get(maxNumberTemp).getTime()));
            setDateText(String.valueOf(mListDataStatisticTime.get(2).getmListDataStatistic().get(maxNumberTemp).getPrice()), mListDataStatisticTime.get(2).getmListDataStatistic().get(maxNumberTemp).getNumberTrip(), mListDataStatisticTime.get(2).getmListDataStatistic().get(maxNumberTemp).getTime());
        } else if (selectMode.equals(Constants.DateAndTime.TYPE_YEAR)) {
            mStartDate = Utilities.dateToStringStatisticStartDate(selectMode, String.valueOf(mListDataStatisticTime.get(3).getmListDataStatistic().get(maxNumberTemp).getTime()));
            mEndDate = Utilities.dateToStringStatisticEndDate(selectMode, String.valueOf(mListDataStatisticTime.get(3).getmListDataStatistic().get(maxNumberTemp).getTime()));
            setDateText(String.valueOf(mListDataStatisticTime.get(3).getmListDataStatistic().get(maxNumberTemp).getPrice()), mListDataStatisticTime.get(3).getmListDataStatistic().get(maxNumberTemp).getNumberTrip(), mListDataStatisticTime.get(3).getmListDataStatistic().get(maxNumberTemp).getTime());
        }

        moveChart(maxNumberTemp - 3);
        bardataset.setHighlightEnabled(true);
        mLoading.setVisibility(View.VISIBLE);
        mylistview.setVisibility(View.GONE);
        presenter.getCompleteList(mStartDate, mEndDate, VERY_LONG_NUMBER--);
    }

    private void loading() {
        mloadingChart.setVisibility(View.VISIBLE);
        chart.setAlpha(0.5f);
    }

    private void loaded() {
        mloadingChart.setVisibility(View.GONE);
        chart.setAlpha(1f);
    }

    private void updateDatabasePageNumber() {
    }
}