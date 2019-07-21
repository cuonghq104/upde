package stp.cuonghq.upde.commons;

import io.reactivex.observers.DisposableObserver;
import stp.cuonghq.upde.data.models.ListResponse;
import stp.cuonghq.upde.data.models.Response;

public class ApiService {

    public static <T> DisposableObserver<Response<T>> disposableObserver(final ApiCallback<T> callback) {
        DisposableObserver<Response<T>> disposableObserver =  new DisposableObserver<Response<T>>() {
            @Override
            public void onNext(Response<T> t) {
                if (t.isSuccess()) {
                     callback.success(t.getData(), t.getMessage());
                } else {
                    callback.failed(t.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                callback.failed(e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        DisposableContainer.add(disposableObserver);
        return disposableObserver;
    }

    public static <T> DisposableObserver<ListResponse<T>> listResponseDisposableObserver(final ApiListCallback <T> callback) {
        return new DisposableObserver<ListResponse<T>>() {
            @Override
            public void onNext(ListResponse<T> t) {
                if (t.isSuccess()) {
                    callback.success(t.getData());
                } else {
                    callback.failed(t.getMessage());
                }
            }

            @Override
            public void onError(Throwable e) {
                callback.failed(e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        };
    }
}
