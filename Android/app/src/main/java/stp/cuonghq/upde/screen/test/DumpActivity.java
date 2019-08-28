package stp.cuonghq.upde.screen.test;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.EndlessRecyclerOnScrollListener;

public class DumpActivity extends AppCompatActivity {

    @BindView(R.id.rv_demo)
    RecyclerView rvDemo;

    DumpAdapter mAdapter;
    EndlessRecyclerOnScrollListener onScrollListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dump);
        ButterKnife.bind(this);
        mAdapter = new DumpAdapter();
        rvDemo.setAdapter(mAdapter);
        rvDemo.setLayoutManager(new LinearLayoutManager(this));
        onScrollListener = new EndlessRecyclerOnScrollListener(
                new EndlessRecyclerOnScrollListener.LoadMoreCallback() {
                    @Override
                    public void loadMore() {
                        mAdapter.updateData();
                        onScrollListener.setLoadingStatus(false);
                    }
                }
        );
        rvDemo.addOnScrollListener(onScrollListener);
    }
}
