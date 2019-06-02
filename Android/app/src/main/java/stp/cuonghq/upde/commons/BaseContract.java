package stp.cuonghq.upde.commons;

import android.arch.lifecycle.Lifecycle;

public class BaseContract {

    interface View {

    }

    interface Presenter<V extends BaseContract.View> {
        void attachLifecycle(Lifecycle lifecycle);
        void detachLifecycle(Lifecycle lifecycle);
        void attachView(V view);
        void detachView();
        V getView();
        boolean isViewAttach();
        void onPresenterDestroy();
    }
}
