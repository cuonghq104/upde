package stp.cuonghq.upde.commons;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class DisposableContainer {

    private static volatile CompositeDisposable compositeDisposable;

    public static void add(Disposable disposable) {
        getCompositeDisposable().add(disposable);
    }

    public static void dispose() {
        if (compositeDisposable != null && !getCompositeDisposable().isDisposed()) {
            synchronized (DisposableContainer.class) {
                getCompositeDisposable().dispose();
            }
        }
    }

    private static CompositeDisposable getCompositeDisposable() {
        if (compositeDisposable == null || compositeDisposable.isDisposed()) {
            synchronized (DisposableContainer.class) {
                compositeDisposable = new CompositeDisposable();
            }
        }
        return compositeDisposable;
    }

    private DisposableContainer() {

    }
}
