package stp.cuonghq.upde.screen.profile;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import stp.cuonghq.upde.R;
import stp.cuonghq.upde.commons.Utilities;
import stp.cuonghq.upde.data.models.LoginData;
import stp.cuonghq.upde.screen.container.ContainerActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements Contract.View {

    @BindView(R.id.btn_log_out)
    LinearLayout btnLogout;

    @BindView(R.id.rl_clip)
    RelativeLayout mRlClip;

    @BindView(R.id.tv_name)
    AppCompatTextView mTvName;

    Presenter mPresenter;
    private LoginData data;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initView(view);
        initData();
        setupUI();
        return view;
    }

    private void initData() {
        mPresenter = new Presenter();
        mPresenter.setView(ProfileFragment.this);
        this.data = mPresenter.getUserData();
    }


    private void setupUI() {
        mRlClip.getBackground().setLevel(2000);
        //mTvName.setText(data.getEmail());
        mTvName.setText("");
    }


    private void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.btn_log_out)
    public void logOut() {
        mPresenter.logOut();
    }

    @Override
    public void logOutSuccess(String msg) {
        Utilities.showToast(getContext(), msg);

        Activity mActivity = getActivity();
        if (mActivity instanceof ContainerActivity) {
            ((ContainerActivity)mActivity).logout();
        }
    }
}
