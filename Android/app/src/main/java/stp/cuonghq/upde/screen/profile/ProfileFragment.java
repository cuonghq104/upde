package stp.cuonghq.upde.screen.profile;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import stp.cuonghq.upde.screen.container.HostContainerActivity;
import stp.cuonghq.upde.screen.container.SupplierContainerActivity;
import stp.cuonghq.upde.screen.editinfo.EditInformationActivity;
import stp.cuonghq.upde.screen.listhome.ListHomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements Contract.View {

    @BindView(R.id.btn_help)
    LinearLayout btnHelp;

    @BindView(R.id.rl_clip)
    RelativeLayout mRlClip;

    @BindView(R.id.tv_name)
    AppCompatTextView mTvName;

    @BindView(R.id.btn_houses)
    LinearLayout mLLHouse;

    @BindView(R.id.tv_version)
    AppCompatTextView mTvVersion;

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
        mTvVersion.setText("Version: ");
        try {
            PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            String version = pInfo.versionName;
            mTvVersion.append(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }


    private void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.btn_logout)
    public void logOut() {
        mPresenter.logOut();
    }

    @OnClick(R.id.btn_edit_information)
    public void editInformation() {
        Intent intent = EditInformationActivity.getInstance(getContext());
        startActivity(intent);
    }

    @OnClick(R.id.btn_houses)
    public void houseList() {
        Intent intent = ListHomeActivity.getInstance(getContext());
        startActivity(intent);
    }

    @Override
    public void logOutSuccess(String msg) {
        Utilities.showToast(getContext(), msg);

        Activity mActivity = getActivity();
        if (mActivity instanceof SupplierContainerActivity) {
            ((SupplierContainerActivity) mActivity).logout();
        }

        else if (mActivity instanceof HostContainerActivity) {
            ((HostContainerActivity) mActivity).logout();
        }
    }
}
