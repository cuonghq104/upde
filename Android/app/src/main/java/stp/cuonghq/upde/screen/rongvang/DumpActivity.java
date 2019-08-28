package stp.cuonghq.upde.screen.rongvang;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import stp.cuonghq.upde.R;

public class DumpActivity extends AppCompatActivity {

    @BindView(R.id.btn_1)
    Button btn1;

    @BindView(R.id.btn_2)
    Button btn2;

    @BindView(R.id.btn_3)
    Button btn3;

    private Intent getIntentFromLink(String link) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (link != null) {
            Uri destination = Uri.parse(link);
            if (destination != null) {
                intent.setData(destination);
                intent.setPackage("com.facebook.katana");
                if (this.getPackageManager().queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY).size() > 0) {
                    Log.d("cuong.hq1","co facebook");
                    return intent;
                } else {
                    Log.d("cuong.hq1","khong facebook");
                    Intent redirIntent = new Intent(Intent.ACTION_VIEW);
                    redirIntent.setData(destination);
                    return redirIntent;
                }
            }
        }
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dump2);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_1)
    public void click1() {
        Intent intent = getIntentFromLink("https://www.facebook.com/events/birthdays/?extra_data%5Bstart_date%5D=2019%2F07%2F12&extra_data%5Bnotif_id%5D=1562894550327717&extra_data%5Bnotif_type%5D=birthday_reminder\n");
        startActivity(intent);
    }

    @OnClick(R.id.btn_2)
    public void click2() {
        Intent intent = getIntentFromLink("https://www.facebook.com/OSTROGaming/videos/451627445688224");
        startActivity(intent);
    }

    @OnClick(R.id.btn_3)
    public void click3() {
        Intent intent = getIntentFromLink("http://www.facebook.com/upit.asia/insights/?section=navPosts");
        startActivity(intent);
    }
}
