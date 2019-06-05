package stp.cuonghq.upde.screen.listhome;

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
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import stp.cuonghq.upde.R;
import stp.cuonghq.upde.data.models.ItemHome;

/**
 * Created by bau.nv on 6/5/2019.
 */

public class ListHomeActivity extends AppCompatActivity implements ListHomeAdapter.OnItemHomeClickListener{

    public static Intent getInstance(Context context) {
        return new Intent(context, ListHomeActivity.class);
    }

    private ListHomeAdapter mHomeAdapter;
    private ArrayList<ItemHome> mList;
    private RecyclerView mRecycleView;
    private AppCompatImageButton btnBack;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_my_home);
        initView();
        initData();
        mHomeAdapter = new ListHomeAdapter(mList, this);
        mHomeAdapter.setListener(this);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager
                .VERTICAL, false));
        mRecycleView.setAdapter(mHomeAdapter);

    }

    private void initData() {
        mList = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            mList.add(new ItemHome(i + " Pham van bach, yen hoa, cau giay, Ha Noi", i + "http://www.jsonschema2pojo.org/"));
        }
    }

    private void initView(){
        mRecycleView = findViewById(R.id.rcv_list_home);
        btnBack = findViewById(R.id.btn_list_home_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListHomeActivity.this.finish();
            }
        });
    }


    @Override
    public void onClickListener(int position) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Link", mList.get(position).getLink());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Copied to clip board", Toast.LENGTH_LONG).show();

    }
}
