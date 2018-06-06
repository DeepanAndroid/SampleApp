package com.example.t2sadmin.sampleapp;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TempScreen extends AppCompatActivity {

    @BindView(R.id.common_list_view)
    CustomRecyclerView mTempListVIew;
    @BindView(R.id.next_btn)
    Button mNextBtn;
    private TempAdapter mTempAdapter;
    private LinearLayoutManager mLayoutManager;

    private ArrayList<TempEntity> mTempList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_recycle_view);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mTempListVIew.setLayoutManager(mLayoutManager);
        mTempList = new ArrayList<>();
        setRadioList();

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CustomRecyclerView.CHOICE_MODE == CustomRecyclerView.CHOICE_MODE_SINGLE) {
                    setCheckboxList();
                } else {
                    setRadioList();
                }
            }
        });
    }

    private void setRadioList() {
        mTempList.clear();
        for (int i = 1; i <= 10; i++) {
            TempEntity mTemp = new TempEntity();
            mTemp.setUserName("Deepan");
            mTemp.setType("Radio");
            mTemp.setUserID(i);
            mTempList.add(mTemp);
        }
        mNextBtn.setText("Multi Selection Next");
        mTempListVIew.setChoiceMode(CustomRecyclerView.CHOICE_MODE_SINGLE);
        setAdapter();
    }

    private void setCheckboxList() {
        mTempList.clear();
        for (int i = 1; i <= 10; i++) {
            TempEntity mTemp = new TempEntity();
            mTemp.setUserName("Chakravarthi");
            mTemp.setType("");
            mTemp.setUserID(i);
            mTempList.add(mTemp);
        }
        mNextBtn.setText("Single Selection Next");
        mTempListVIew.setChoiceMode(CustomRecyclerView.CHOICE_MODE_MULTIPLE);
        setAdapter();
    }

    private void setAdapter() {
        if (mTempAdapter != null) {
            mTempAdapter.notifyDataSetChanged();
        } else {
            mTempAdapter = new TempAdapter(this, mTempList);
            mTempListVIew.setAdapter(mTempAdapter);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
