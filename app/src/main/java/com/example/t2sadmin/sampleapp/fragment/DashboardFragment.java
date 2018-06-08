package com.example.t2sadmin.sampleapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.t2sadmin.sampleapp.activity.HomeFragmentActivity;
import com.example.t2sadmin.sampleapp.main.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DashboardFragment extends BaseFragment {

    @BindView(R.id.frag_dashboard)
    CoordinatorLayout mCoordinatorLayout;

    View mRootView;

    private int[] tabTitle = {
            R.string.ride_offers,
            R.string.ride_requests};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.frag_dashboard_screen, container, false);
        ButterKnife.bind(this, mRootView);
        setupUI(mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        ((HomeFragmentActivity) getActivity()).setTitleTxt(getString(R.string.home));
    }


}
