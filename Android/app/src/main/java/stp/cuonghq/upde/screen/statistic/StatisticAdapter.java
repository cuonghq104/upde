package stp.cuonghq.upde.screen.statistic;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import stp.cuonghq.upde.R;

/**
 * Created by cuong.hq1 on 5/31/2019.
 */

public class StatisticAdapter extends RecyclerView.Adapter<StatisticAdapter.StatisticViewHolder>{

    private ArrayList<String> mList = new ArrayList<>();

    StatisticAdapter (ArrayList<String> someList){
        mList = someList;
    }

    @NonNull
    @Override
    public StatisticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_statistic_item,parent,false);

        return new StatisticViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatisticViewHolder holder, int position) {
        holder.tv_sample.setText("item "+position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


     class StatisticViewHolder extends RecyclerView.ViewHolder{
         TextView tv_sample;

        public StatisticViewHolder(View itemView) {
            super(itemView);
            tv_sample = itemView.findViewById(R.id.tv_statistic_sample);
        }
    }
}
