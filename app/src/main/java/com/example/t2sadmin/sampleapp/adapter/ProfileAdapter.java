package com.example.t2sadmin.sampleapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.t2sadmin.sampleapp.R;
import com.example.t2sadmin.sampleapp.activity.ProfileActivity;
import com.example.t2sadmin.sampleapp.customviews.CustomRecyclerView;
import com.example.t2sadmin.sampleapp.model.ProfileChildModel;
import com.example.t2sadmin.sampleapp.model.ProfileMainModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProfileAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<ProfileMainModel> mTempList;
    private LayoutInflater mInflater;


    public ProfileAdapter(Context context, ArrayList<ProfileMainModel> tempList) {
        mContext = context;
        mTempList = tempList;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = mInflater.inflate(R.layout.adap_profile, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int pos) {
        ViewHolder mHolder = (ViewHolder) holder;
        mHolder.mChildLay.setTag(pos);
        mHolder.mAddIcon.setTag(mHolder.mChildLay);
        mHolder.mAddIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout childLay = (LinearLayout) view.getTag();
                int selPos = (int) childLay.getTag();

                ArrayList<ProfileChildModel> tempList = mTempList.get(selPos).getChildList();
                ProfileChildModel model = new ProfileChildModel();
                model.setType("Home");
                model.setValue("");
                tempList.add(model);
                mTempList.get(selPos).setChildList(tempList);
                addChildView(selPos,childLay, tempList);
            }
        });

        addChildView(pos,mHolder.mChildLay, mTempList.get(pos).getChildList());
    }

    private void addChildView(final int parentPos,final LinearLayout childLay, ArrayList<ProfileChildModel> tempList) {
        childLay.removeAllViews();
        for (int i = 0; i < tempList.size(); i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.adap_child_profile, null, false);
            Spinner mSpinner = view.findViewById(R.id.spinner);
            EditText mEditTxt = view.findViewById(R.id.editTxt);
            ImageView mRemoveIcon = view.findViewById(R.id.removeIcon);
            mEditTxt.setText(tempList.get(i).getValue());

            mEditTxt.setTag(i);
            mEditTxt.addTextChangedListener(new CustomTextWatcher(mEditTxt,parentPos,i));
            if (i == tempList.size() - 1) {
                mEditTxt.setFocusable(true);
                mEditTxt.requestFocus();
            }
            mRemoveIcon.setTag(i);
            mRemoveIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int selPos = (int) view.getTag();
                    ArrayList<ProfileChildModel> tempList = mTempList.get(parentPos).getChildList();
                    tempList.remove(selPos);
                    mTempList.get(parentPos).setChildList(tempList);
                    addChildView(parentPos,childLay,mTempList.get(parentPos).getChildList());
                }
            });
            childLay.addView(view);
        }
        ((ProfileActivity)mContext).scrollToBottom();
    }


    @Override
    public int getItemCount() {
        return mTempList.size();
    }

    public class ViewHolder extends CustomRecyclerView.ViewHolder {

        @BindView(R.id.addIcon)
        ImageView mAddIcon;
        @BindView(R.id.childLay)
        LinearLayout mChildLay;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    private class CustomTextWatcher implements TextWatcher {

        private View view;
        private int parentPos,childPos;

        private CustomTextWatcher(View view, int parentPos,int childPos) {
            this.view = view;
            this.parentPos = parentPos;
            this.childPos = childPos;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            if (view.getTag() != null) {
                int selPos = (int) view.getTag();
                ArrayList<ProfileChildModel> tempList = mTempList.get(parentPos).getChildList();
                if (selPos < tempList.size()) {
                    String str = editable.toString().trim();
                    tempList.get(selPos).setValue(str);
                }
                mTempList.get(parentPos).setChildList(tempList);
            }
        }
    }
}
