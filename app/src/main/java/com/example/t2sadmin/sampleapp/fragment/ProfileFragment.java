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
import com.example.t2sadmin.sampleapp.main.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileFragment extends BaseFragment {


    @BindView(R.id.profile_parent)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.edit_icon)
    ImageView mEditIcon;
    @BindView(R.id.name_edt)
    EditText mNameEdt;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_profile_screen, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        setupUI(mCoordinatorLayout);
        mNameEdt.setEnabled(false);
        mNameEdt.setFocusableInTouchMode(false);
        showSnackBar(mCoordinatorLayout, getString(R.string.temp_alert));
    }

    @OnClick({R.id.edit_icon, R.id.update_btn, R.id.logout_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_icon:
                break;
            case R.id.update_btn:
                mNameEdt.setEnabled(false);
                mNameEdt.setFocusableInTouchMode(false);
                break;
            case R.id.logout_btn:

                break;

        }
    }


}