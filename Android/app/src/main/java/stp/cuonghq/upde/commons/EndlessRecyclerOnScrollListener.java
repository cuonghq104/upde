package stp.cuonghq.upde.commons;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
