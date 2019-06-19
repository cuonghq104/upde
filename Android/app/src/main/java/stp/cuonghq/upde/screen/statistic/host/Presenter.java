package stp.cuonghq.upde.screen.statistic.host;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import stp.cuonghq.upde.commons.ApiCallback;
import stp.cuonghq.upde.commons.BasePresenter;
import stp.cuonghq.upde.commons.Constants;
import stp.cuonghq.upde.data.models.BookingList;
import stp.cuonghq.upde.data.models.StatisticGetAllPriceResponce;
import stp.cuonghq.upde.data.models.dbentities.DayStatisticEntity;
import stp.cuonghq.upde.data.models.dbentities.MonthStatisticEntity;
import stp.cuonghq.upde.data.models.dbentities.WeekStatisticEntity;
import stp.cuonghq.upde.data.models.dbentities.YearStatisticEntity;
import stp.cuonghq.upde.data.repositories.TripRepository;

public class Presenter extends BasePresenter<StatisticFragment> implements Contract.Presenter {

    private TripRepository mRepository;

    public Presenter() {
        mRepository = TripRepository.getInstance();
    }

    @Override
    public void getStatistic(String type, int page) {
        mRepository.getAllPrice(type, page, new ApiCallback<StatisticGetAllPriceResponce>() {
            @Override
            public void success(StatisticGetAllPriceResponce data, String msg) {
                getView().getStatisticSuccess(data);
            }

            @Override
            public void failed(String msg) {
                getView().getStatisticFailed(msg);
            }
        });
    }

    @Override
    public void getCompleteList(String time_begin, String time_end, long page) {

        mRepository.getAllTripCompleteByTimeObservale(time_begin, time_end, page, new ApiCallback<BookingList>() {
            @Override
            public void success(BookingList data, String msg) {
                getView().getCompleteListSuccess(data);
            }

            @Override
            public void failed(String msg) {
                getView().getCompleteListFailed(msg);
            }
        });
    }

    @Override
    public <T> List getAll(String mType) {
        if (mType.equals(Constants.DateAndTime.TYPE_DAY)) {
            return new ArrayList<DayStatisticEntity>(mRepository.getAllDay());
        } else if (mType.equals(Constants.DateAndTime.TYPE_WEEK)) {
            return new ArrayList<WeekStatisticEntity>(mRepository.getAllWeek());
        } else if (mType.equals(Constants.DateAndTime.TYPE_MONTH)) {
            return new ArrayList<MonthStatisticEntity>(mRepository.getAllMonth());
        } else if (mType.equals(Constants.DateAndTime.TYPE_YEAR)) {
            return new ArrayList<YearStatisticEntity>(mRepository.getAllYear());
        }
        return null;
    }

    @Override
    public void insert(String time, String price, String tripnumber, String type, int pagenumber) {
        Log.d("hehe", "insert into database time = " + time + " price = " + price + " tripnumber " + tripnumber + "type " + type + " page number = " + pagenumber);
        if (type.equals(Constants.DateAndTime.TYPE_DAY)) {
            mRepository.insertDay(time, price, pagenumber, tripnumber);
        } else if (type.equals(Constants.DateAndTime.TYPE_WEEK)) {
            mRepository.insertWeek(time, price, pagenumber, tripnumber);
        } else if (type.equals(Constants.DateAndTime.TYPE_MONTH)) {
            mRepository.insertMonth(time, price, pagenumber, tripnumber);
        } else if (type.equals(Constants.DateAndTime.TYPE_YEAR)) {
            mRepository.insertYear(time, price, pagenumber, tripnumber);
        }

    }

    @Override
    public int getMaxBy(String type) {
        if (type.equals(Constants.DateAndTime.TYPE_DAY)) {
            return  mRepository.getMaxByDay();
        } else if (type.equals(Constants.DateAndTime.TYPE_WEEK)) {
            return  mRepository.getMaxByWeek();
        } else if (type.equals(Constants.DateAndTime.TYPE_MONTH)) {
            return mRepository.getMaxByMonth();
        } else if (type.equals(Constants.DateAndTime.TYPE_YEAR)) {
            return mRepository.getMaxByYear();
        }
        return 0;
    }

    @Override
    public <T> T getCurrent(String type) {
        if (type.equals(Constants.DateAndTime.TYPE_DAY)) {
            return (T) mRepository.getCurrentDay(type);
        } else if (type.equals(Constants.DateAndTime.TYPE_WEEK)) {
            return (T) mRepository.getCurrentWeek(type);
        } else if (type.equals(Constants.DateAndTime.TYPE_MONTH)) {
            return (T) mRepository.getCurrentMonth(type);
        } else if (type.equals(Constants.DateAndTime.TYPE_YEAR)) {
            return (T) mRepository.getCurrentYear(type);
        }
        return null;
    }

    @Override
    public void update(String time, String price, String tripnumber, String type, int pagenumber) {
        if (type.equals(Constants.DateAndTime.TYPE_DAY)) {
            mRepository.updateDayPageNumber(time, price, pagenumber, tripnumber);
        } else if (type.equals(Constants.DateAndTime.TYPE_WEEK)) {
            mRepository.updateWeekPageNumber(time, price, pagenumber, tripnumber);
        } else if (type.equals(Constants.DateAndTime.TYPE_MONTH)) {
            mRepository.updateMonthPageNumber(time, price, pagenumber, tripnumber);
        } else if (type.equals(Constants.DateAndTime.TYPE_YEAR)) {
            mRepository.updateYearPageNumber(time, price, pagenumber, tripnumber);
        }
    }

    @Override
    public void delete(String type){
        if (type.equals(Constants.DateAndTime.TYPE_DAY)) {
            mRepository.deleteDay();
        } else if (type.equals(Constants.DateAndTime.TYPE_WEEK)) {
            mRepository.deleteWeek();
        } else if (type.equals(Constants.DateAndTime.TYPE_MONTH)) {
            mRepository.deleteMonth();
        } else if (type.equals(Constants.DateAndTime.TYPE_YEAR)) {
            mRepository.deleteYear();
        }
    }

}
