package stp.cuonghq.upde.screen.listhome;

import stp.cuonghq.upde.data.models.ListItemHome;

/**
 * Created by bau.nv on 6/6/2019.
 */

public class Contract {

    interface View {
        void getListHomeSuccess(ListItemHome list);
        void getListHomeFailed(String msg);
    }

    interface Presenter {
        void getListHome();
    }

}
