package stp.cuonghq.upde.commons;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import stp.cuonghq.upde.screen.start.StartActivity;

public abstract class BasePresenter<V extends BaseContract.View> implements LifecycleObserver, BaseContract.Presenter<V> {

    private Bundle stateBundle;
    private V view;

    @Override
    public V getView() {
        return view;
    }

    @Override
    public void attachLifecycle(Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    @Override
    public void detachLifecycle(Lifecycle lifecycle) {
        lifecycle.removeObserver(this);
    }

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public boolean isViewAttach() {
        return view != null;
    }

    public Bundle getStateBundle() {
        return stateBundle == null ? stateBundle = new Bundle() : stateBundle;
    }

    @CallSuper
    @Override
    public void onPresenterDestroy() {
        if (stateBundle != null && !stateBundle.isEmpty()) {
            stateBundle.clear();
        }
    }

    public void unauthorization() {
        V view = getView();
        if (view instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) view;
            Intent intent = StartActivity.getInstance(activity);
            activity.startActivity(intent);
            activity.finish();
        }
        else if (view instanceof Fragment) {
            Fragment fragment = (Fragment) view;
            Intent intent = StartActivity.getInstance(fragment.getContext());
            fragment.startActivity(intent);
            fragment.getActivity().finish();
        }
    }
}
