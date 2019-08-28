package stp.cuonghq.upde.screen.listhome;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import stp.cuonghq.upde.R;
import stp.cuonghq.upde.data.models.ItemHome;

/**
 * Created by bau.nv on 6/5/2019.
 */

public class ListHomeAdapter extends RecyclerView.Adapter<ListHomeAdapter.ItemHomeViewHolder> {

    private List<ItemHome> mListHome;
    private Context mContext;
    private OnItemHomeClickListener listener;

    ListHomeAdapter(Context context){
        this.mContext = context;
    }

    public void setList(List<ItemHome> list){
        this.mListHome = list;
        notifyDataSetChanged();
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
        final ItemHome item = mListHome.get(position);
        holder.tvAddress.setText(item.getAddress());
        holder.tvLink.setText(item.getLink());
        holder.tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(mContext)
                        .setIcon(R.drawable.ic_warning_black_24dp)
                        .setTitle(mContext.getString(R.string.title_confirm_dialog))
                        .setMessage(String.format(mContext.getResources().getString(R.string.content_confirm_dialog), item.getLink()))
                        .setPositiveButton(mContext.getResources().getString(R.string.title_access), new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String url = item.getLink();
                                if (!url.startsWith("http://") && !url.startsWith("https://"))
                                    url = "http://" + url;

                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                if (mContext instanceof AppCompatActivity) {
                                    ((AppCompatActivity) mContext).startActivity(intent);
                                }
                            }

                        })
                        .setNegativeButton(mContext.getResources().getString(R.string.title_cancel), null)
                        .show();


            }
        });
        holder.btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickListener(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return (mListHome == null ? 0 : mListHome.size());
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
