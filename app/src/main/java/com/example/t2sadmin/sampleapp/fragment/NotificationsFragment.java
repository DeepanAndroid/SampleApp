package com.example.t2sadmin.sampleapp.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.t2sadmin.sampleapp.R;
import com.example.t2sadmin.sampleapp.main.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsFragment extends BaseFragment {

    @BindView(R.id.common_recycle_parent)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.common_list_view)
    RecyclerView mRideOffersListView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.common_recycle_view, container, false);
        ButterKnife.bind(this, mRootView);
        setupUI(mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initView() {
        showSnackBar(mCoordinatorLayout, getString(R.string.temp_alert));
    }


}
