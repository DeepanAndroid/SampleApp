package com.example.t2sadmin.sampleapp.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.t2sadmin.sampleapp.R;
import com.example.t2sadmin.sampleapp.activity.HomeFragmentActivity;
import com.example.t2sadmin.sampleapp.main.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileFragment extends BaseFragment {


    @BindView(R.id.profile_parent)
    CoordinatorLayout mCoordinatorLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_profile_screen, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    @Override
    public void onRefreshFragment() {
        super.onRefreshFragment();
        initView();
    }

    private void initView() {
        setupUI(mCoordinatorLayout);
        ((HomeFragmentActivity) getActivity()).setTitleTxt(getString(R.string.profile));
        showSnackBar(mCoordinatorLayout, getString(R.string.temp_alert));
    }

}