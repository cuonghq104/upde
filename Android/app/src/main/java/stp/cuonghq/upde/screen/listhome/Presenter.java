package stp.cuonghq.upde.screen.listhome;

import stp.cuonghq.upde.commons.ApiCallback;
import stp.cuonghq.upde.data.models.ListItemHome;
import stp.cuonghq.upde.data.repositories.TripRepository;

/**
 * Created by bau.nv on 6/6/2019.
 */

public class Presenter  implements  Contract.Presenter{

    private static final String TAG = "Presenter Listhome";

    private Contract.View mView;

    private TripRepository tripRepository;

    public Presenter(){
        tripRepository = TripRepository.getInstance();
    }

    public void setView(Contract.View mView) {
        this.mView = mView;
    }

    @Override
    public void getListHome() {
        tripRepository.getHotelLinks(new ApiCallback<ListItemHome>() {
            @Override
            public void success(ListItemHome data, String msg) {
                mView.getListHomeSuccess(data);
            }

            @Override
            public void failed(String msg) {
                mView.getListHomeFailed("baunv "+msg);
            }
        });
    }
}
