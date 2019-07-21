package stp.cuonghq.upde.screen.home;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import stp.cuonghq.upde.R;
import stp.cuonghq.upde.data.models.BookingResp;
import stp.cuonghq.upde.screen.container.SupplierContainerActivity;
import stp.cuonghq.upde.screen.container.NotificationReceiver;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    @BindView(R.id.vp_home)
    ViewPager mVpHome;
    @BindView(R.id.tl_home)
    TabLayout mTlHome;

    HomePagerAdapter mAdapter;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initData();
        setupUI();

        return view;
    }

    private void initData() {
        if (getActivity() instanceof SupplierContainerActivity) {
            ((SupplierContainerActivity) getActivity()).setBookingReceive(new NotificationReceiver() {
                @Override
                public void receiveBooking(BookingResp booking) {
                    mAdapter.addToBookingList(booking);
                }
            });
            ((SupplierContainerActivity) getActivity()).setConfirmReceive(new NotificationReceiver() {
                @Override
                public void receiveBooking(BookingResp booking) {
                    mAdapter.removeFromBookingList(booking);
                    mAdapter.addToConfirmList(booking);
                }
            });
            ((SupplierContainerActivity) getActivity()).setCompleteReceive(new NotificationReceiver() {
                @Override
                public void receiveBooking(BookingResp booking) {
                    mAdapter.removeFromConfirmList(booking);
                    mAdapter.addToCompleteList(booking);
                }
            });
        }
    }

    private void setupUI() {
        mAdapter = new HomePagerAdapter(getChildFragmentManager(), getContext());
        mVpHome.setAdapter(mAdapter);
        mTlHome.setupWithViewPager(mVpHome);
        mVpHome.setOffscreenPageLimit(3);

    }

    public void moveToConfirmList(BookingResp booking) {
        mAdapter.addToConfirmList(booking);
    }

    public void moveToCompleteList(BookingResp booking) {
        mAdapter.addToCompleteList(booking);
    }
}
