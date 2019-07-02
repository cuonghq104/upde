package stp.cuonghq.upde.commons;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    private boolean mLoading;
    private LoadMoreCallback mCallback;

    public EndlessRecyclerOnScrollListener(LoadMoreCallback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
        int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                .findFirstVisibleItemPosition();
        if (!mLoading && (firstVisibleItemPosition + visibleItemCount) == totalItemCount) {
            mLoading = true;
            mCallback.loadMore();
        }

    }

    public void setLoadingStatus(boolean status) {
        mLoading = status;
    }

    public interface LoadMoreCallback {
        void loadMore();
    }
}
