package stp.cuonghq.upde.screen.start;

import android.content.Context;
import android.content.Intent;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.widget.FrameLayout;

import butterknife.BindView;
import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.NonPresenterActivity;
import stp.cuonghq.upde.commons.Utilities;
import stp.cuonghq.upde.screen.start.signin.SignInFragment;

public class StartActivity extends NonPresenterActivity {

    public static Intent getInstance(Context context) {
        Intent intent = new Intent(context, StartActivity.class);
        return intent;
    }

    @BindView(R.id.fl_container)
    FrameLayout flContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        changeFragment(SignInFragment.create());
    }


    public void changeFragment(Fragment fragment) {
        Utilities.changeFragment(getSupportFragmentManager(), R.id.fl_container, fragment);
    }
}
