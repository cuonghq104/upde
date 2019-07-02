package stp.cuonghq.upde.screen.test;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import stp.cuonghq.upde.R;

public class DumpAdapter extends RecyclerView.Adapter<DumpAdapter.DumpViewHolder> {

    int max = 0;
    List<Integer> list;

    DumpAdapter() {
        max = 100;
        list = new ArrayList<>();
        for (int i = 1; i <= max; i++) {
            list.add(i);
        }
    }

    void updateData() {
        for (int i = max + 1; i <= max + 100; i++) {
            list.add(i);
        }
        max += 100;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DumpViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_demo, viewGroup, false);
        return new DumpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DumpViewHolder dumpViewHolder, int i) {
        int t = list.get(i);
        dumpViewHolder.bind(t);
    }

    @Override
    public int getItemCount() {
        return (list == null) ? 0 : list.size();
    }

    class DumpViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        AppCompatTextView mTvTitle;

        public DumpViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(int index) {
            mTvTitle.setText(index + "");
        }
    }
}
