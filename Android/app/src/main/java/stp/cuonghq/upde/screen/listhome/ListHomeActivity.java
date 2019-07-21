package stp.cuonghq.upde.screen.listhome;

import android.app.ListActivity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import stp.cuonghq.upde.R;
import stp.cuonghq.upde.data.models.ItemHome;
import stp.cuonghq.upde.data.models.ListItemHome;

/**
 * Created by bau.nv on 6/5/2019.
 */

public class ListHomeActivity extends AppCompatActivity implements ListHomeAdapter.OnItemHomeClickListener,Contract.View{

    public static Intent getInstance(Context context) {
        return new Intent(context, ListHomeActivity.class);
    }

    private ListHomeAdapter mHomeAdapter;
    private List<ItemHome> mList;
    private RecyclerView mRecycleView;
    private AppCompatImageButton btnBack;
    private TextView tvNoData;
    private Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_my_home);
        initView();
        initData();
        mHomeAdapter = new ListHomeAdapter(ListHomeActivity.this);
        mHomeAdapter.setListener(this);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager
                .VERTICAL, false));
        mRecycleView.setAdapter(mHomeAdapter);
    }

    private void initData() {
        presenter.getListHome();
    }

    private void initView(){
        tvNoData = findViewById(R.id.tv_home_nodata);
        mRecycleView = findViewById(R.id.rcv_list_home);
        mRecycleView.setVisibility(View.GONE);
        btnBack = findViewById(R.id.btn_list_home_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListHomeActivity.this.finish();
            }
        });
        presenter = new Presenter();
        presenter.setView(ListHomeActivity.this);
    }


    @Override
    public void onClickListener(int position) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Link", mList.get(position).getLink());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Copied to clip board", Toast.LENGTH_LONG).show();

    }

    @Override
    public void getListHomeSuccess(ListItemHome list) {
        mList = list.getList();
        mHomeAdapter.setList(mList);
        if(mList.size() == 0){
            tvNoData.setVisibility(View.VISIBLE);
            mRecycleView.setVisibility(View.GONE);
        } else {
            tvNoData.setVisibility(View.GONE);
            mRecycleView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void getListHomeFailed(String msg) {
        Toast.makeText(this, "Get List Home Failed "+msg, Toast.LENGTH_LONG).show();
    }
}
