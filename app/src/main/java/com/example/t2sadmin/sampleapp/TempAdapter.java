package com.example.t2sadmin.sampleapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TempAdapter extends
        CustomRecyclerView.Adapter<CustomRecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<TempEntity> mTempList;
    private LayoutInflater mInflater;


    public TempAdapter(Context context, ArrayList<TempEntity> tempList) {
        mContext = context;
        mTempList = tempList;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public CustomRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = mInflater.inflate(R.layout.temp_radio_adap, parent, false);
        switch (viewType) {
            case CustomRecyclerView.CHOICE_MODE_SINGLE:
                mView = mInflater.inflate(R.layout.temp_radio_adap, parent, false);
                return new RadioViewHolder(mView);
            case CustomRecyclerView.CHOICE_MODE_MULTIPLE:
                mView = mInflater.inflate(R.layout.temp_checkbox_adap, parent, false);
                return new CheckBoxViewHolder(mView);
        }
        return new RadioViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecyclerView.ViewHolder mHolder, int pos) {
        TempEntity mTempEntity = mTempList.get(pos);
        switch (getItemViewType(pos)) {
            case CustomRecyclerView.CHOICE_MODE_SINGLE:
                RadioViewHolder mViewHolder = (RadioViewHolder) mHolder;
                mViewHolder.mRadioBtn.setText(mTempEntity.getUserName());
                mViewHolder.mRadioBtn.setChecked(mTempEntity.isSelected());
                mViewHolder.mRadioBtn.setTag(pos);
                mViewHolder.mRadioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton v, boolean isChecked) {
                        int selPos = (int) v.getTag();
                        if (v.isPressed()) {
                            for (TempEntity mEntity : mTempList) {
                                mEntity.setSelected(false);
                            }
                            mTempList.get(selPos).setSelected(true);
                            notifyDataSetChanged();
                        }

                    }
                });
                break;
            case CustomRecyclerView.CHOICE_MODE_MULTIPLE:
                CheckBoxViewHolder mCheckBoxViewHolder = (CheckBoxViewHolder) mHolder;
                mCheckBoxViewHolder.mCheckBox.setText(mTempEntity.getUserName());
                mCheckBoxViewHolder.mCheckBox.setChecked(mTempEntity.isSelected());
                mCheckBoxViewHolder.mCheckBox.setTag(pos);
                mCheckBoxViewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton v, boolean isChecked) {
                        int selPos = (int) v.getTag();
                        if (v.isPressed()) {
                            mTempList.get(selPos).setSelected(isChecked);
                            notifyDataSetChanged();
                        }

                    }
                });
                break;
            default:
                break;
        }
    }

    public class RadioViewHolder extends CustomRecyclerView.ViewHolder {

        @BindView(R.id.radio_btn)
        RadioButton mRadioBtn;

        RadioViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);


        }


    }


    public class CheckBoxViewHolder extends CustomRecyclerView.ViewHolder {

        @BindView(R.id.checkbox)
        CheckBox mCheckBox;

        CheckBoxViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public int getItemCount() {
        return mTempList.size();
    }

    @Override
    public int getItemViewType(int pos) {
        if (CustomRecyclerView.CHOICE_MODE == CustomRecyclerView.CHOICE_MODE_SINGLE) {
            return CustomRecyclerView.CHOICE_MODE_SINGLE;
        } else {
            return CustomRecyclerView.CHOICE_MODE_MULTIPLE;
        }
    }
}
