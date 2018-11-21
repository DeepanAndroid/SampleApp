package com.example.t2sadmin.sampleapp.activity;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.t2sadmin.sampleapp.R;
import com.example.t2sadmin.sampleapp.adapter.ProfileAdapter;
import com.example.t2sadmin.sampleapp.main.BaseActivity;
import com.example.t2sadmin.sampleapp.model.ProfileChildModel;
import com.example.t2sadmin.sampleapp.model.ProfileMainModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends BaseActivity {

    @BindView(R.id.common_list_view)
    RecyclerView mTempListVIew;
    private ProfileAdapter mProfileAdapter;
    private LinearLayoutManager mLayoutManager;

    private ArrayList<ProfileMainModel> mTempList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_profile_screen);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mTempListVIew.setLayoutManager(mLayoutManager);
        mTempList = new ArrayList<>();
        setProfileList();


    }

    public void scrollToBottom() {
        mTempListVIew.smoothScrollBy(0, mTempListVIew.getHeight());
    }

    private void setProfileList() {
        mTempList.clear();
        for (int i = 1; i <= 2; i++) {
            ProfileMainModel mTemp = new ProfileMainModel();
            mTemp.setTitle("Profile - " + i);
            mTemp.setChildList(new ArrayList<ProfileChildModel>());
            mTempList.add(mTemp);
        }
        setAdapter();
    }

    private void setAdapter() {
        if (mProfileAdapter != null) {
            mProfileAdapter.notifyDataSetChanged();
        } else {
            mProfileAdapter = new ProfileAdapter(this, mTempList);
            mTempListVIew.setAdapter(mProfileAdapter);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
