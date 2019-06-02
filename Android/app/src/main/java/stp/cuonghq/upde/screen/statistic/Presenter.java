package stp.cuonghq.upde.screen.statistic;

import stp.cuonghq.upde.commons.BaseContract;
import stp.cuonghq.upde.commons.BasePresenter;
import stp.cuonghq.upde.data.repositories.TripRepository;

public class Presenter extends BasePresenter<StatisticFragment> implements Contract.Presenter {

    private TripRepository repository;

    @Override
    public void getStatistic(String byPeriod) {

    }
}
