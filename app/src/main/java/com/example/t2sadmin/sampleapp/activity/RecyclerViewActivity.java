package com.example.t2sadmin.sampleapp.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;

import com.example.t2sadmin.sampleapp.R;
import com.example.t2sadmin.sampleapp.adapter.RecyclerViewAdapter;
import com.example.t2sadmin.sampleapp.model.RecyclerViewEntity;
import com.example.t2sadmin.sampleapp.customviews.CustomRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewActivity extends AppCompatActivity {

    @BindView(R.id.common_list_view)
    CustomRecyclerView mTempListVIew;
    @BindView(R.id.next_btn)
    Button mNextBtn;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private LinearLayoutManager mLayoutManager;

    private ArrayList<RecyclerViewEntity> mTempList;

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
            RecyclerViewEntity mTemp = new RecyclerViewEntity();
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
            RecyclerViewEntity mTemp = new RecyclerViewEntity();
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
        if (mRecyclerViewAdapter != null) {
            mRecyclerViewAdapter.notifyDataSetChanged();
        } else {
            mRecyclerViewAdapter = new RecyclerViewAdapter(this, mTempList);
            mTempListVIew.setAdapter(mRecyclerViewAdapter);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
