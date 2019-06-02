package stp.cuonghq.upde.screen.statistic;

public class Contract {

    interface View {

    }

    interface Presenter {
        void getStatistic(String byPeriod);
    }
}
