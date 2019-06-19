package stp.cuonghq.upde.screen.statistic.host;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import stp.cuonghq.upde.data.models.BookingList;
import stp.cuonghq.upde.data.models.StatisticGetAllPriceResponce;
import stp.cuonghq.upde.data.models.dbentities.WeekStatisticEntity;

public class Contract {

    interface View {
        void getCompleteListSuccess(BookingList list);
        void getCompleteListFailed(String msg);

        void getStatisticSuccess(StatisticGetAllPriceResponce list);
        void getStatisticFailed(String msg);
    }

    interface Presenter {
        void getStatistic(String type, int page);
        void getCompleteList(String time_begin,String time_end, long page);

        <T> List getAll(String mType);
        void insert(String time , String price , String tripnumber , String type , int pagenumber);
        int getMaxBy(String type);
        <T> T getCurrent(String type);
        void update(String time , String price , String tripnumber , String type , int pagenumber);
        void delete(String type);
    }
}
