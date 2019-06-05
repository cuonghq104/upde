package stp.cuonghq.upde.screen.listhome;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import stp.cuonghq.upde.R;
import stp.cuonghq.upde.data.models.ItemHome;

/**
 * Created by bau.nv on 6/5/2019.
 */

public class ListHomeAdapter extends RecyclerView.Adapter<ListHomeAdapter.ItemHomeViewHolder> {

    private ArrayList<ItemHome> mListHome;
    private Context mContext;
    private OnItemHomeClickListener listener;

    ListHomeAdapter(ArrayList<ItemHome> list, Context context){
        this.mListHome = list;
        this.mContext = context;
    }

    public void setListener(OnItemHomeClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public ItemHomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_home_item,viewGroup,false);
        return new ItemHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHomeViewHolder holder, final int position) {
        ItemHome item = mListHome.get(position);
        holder.tvAddress.setText(item.getAddress());
        holder.tvLink.setText(item.getLink());
        holder.btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickListener(holder.getAdapterPosition());
            }
        });


    }

    @Override
    public int getItemCount() {
        return mListHome.size();
    }


    class ItemHomeViewHolder extends RecyclerView.ViewHolder{
        TextView tvAddress, tvLink;
        Button btnCopy;

        public ItemHomeViewHolder(View itemView) {
            super(itemView);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvLink = itemView.findViewById(R.id.tv_link);
            btnCopy = itemView.findViewById(R.id.btn_copy);
        }
    }

    public interface OnItemHomeClickListener {
        void onClickListener(int position);
    }

}
