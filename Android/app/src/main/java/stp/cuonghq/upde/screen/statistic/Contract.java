package stp.cuonghq.upde.screen.statistic;

import stp.cuonghq.upde.data.models.BookingList;
import stp.cuonghq.upde.data.models.StatisticGetAllPriceResponce;

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
    }
}
