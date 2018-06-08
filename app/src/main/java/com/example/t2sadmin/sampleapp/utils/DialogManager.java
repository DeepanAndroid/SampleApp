package com.example.t2sadmin.sampleapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.t2sadmin.sampleapp.R;
import com.example.t2sadmin.sampleapp.interfaces.CommonCallback;
import com.example.t2sadmin.sampleapp.interfaces.CommonReturnCallback;
import com.example.t2sadmin.sampleapp.interfaces.ImagePickerCallback;
import com.example.t2sadmin.sampleapp.main.BaseFragment;
import com.example.t2sadmin.sampleapp.main.BasePopup;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;


public class DialogManager extends BasePopup {

    static Dialog mDialog;
    static Dialog mAlertDialog;
    static Dialog mAlertDialogWithCallback;
    static Dialog mProgressDialog;
    static TextView mDateTxt;
    static Dialog mImageViewDialog;
    private static TimePickerDialog mTimePickerDialog;

    public static void showAlertDialog(Context mContext, String mMessage) {

        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
        mAlertDialog = getDialog(mContext, R.layout.popup_common_alert);
        mAlertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationFromBottom;
        TextView mMessageTxt = mAlertDialog
                .findViewById(R.id.msg_txt);
        Button mOkBtn = mAlertDialog.findViewById(R.id.ok_btn);

        mMessageTxt.setText(mMessage);
        mOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
        mAlertDialog.show();
    }

    public static void showAlertDialogWithCallback(Context mContext,
                                                   final CommonCallback mCallback, String mMessage) {
        if (mAlertDialogWithCallback != null
                && mAlertDialogWithCallback.isShowing()) {
            mAlertDialogWithCallback.dismiss();
        }
        mAlertDialogWithCallback = getDialog(mContext,
                R.layout.popup_common_alert);
        mAlertDialogWithCallback.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationFromBottom;
        mAlertDialogWithCallback.setCancelable(false);
        mAlertDialogWithCallback.setCanceledOnTouchOutside(false);

        TextView msgTxt = mAlertDialogWithCallback.findViewById(R.id.msg_txt);
        Button mOkBtn = mAlertDialogWithCallback.findViewById(R.id.ok_btn);
        msgTxt.setText(mMessage);
        mOkBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mAlertDialogWithCallback.dismiss();
                mCallback.onOkClick();
            }
        });
        mAlertDialogWithCallback.show();
    }

    public static Dialog getDialog(Context mContext, int mLayout) {

        Dialog mDialog = new Dialog(mContext, R.style.AppTheme);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mDialog.setContentView(mLayout);
        mDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        mDialog.getWindow().setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);

        return mDialog;
    }

    public static void showOptionPopupWithCallback(Context context, String message, String firstBtnName, String secondBtnName,
                                                   final CommonCallback mCallback) {
        if (mAlertDialogWithCallback != null
                && mAlertDialogWithCallback.isShowing()) {
            mAlertDialogWithCallback.dismiss();
        }
        mAlertDialogWithCallback = getDialog(context, R.layout.popup_option_alert);
        mAlertDialogWithCallback.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationFromBottom;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = mAlertDialogWithCallback.getWindow();
        if (window != null) {
            lp.copyFrom(window.getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }
        TextView msgTxt;
        Button firstBtn, secondBtn;

        //Init View
        msgTxt = mAlertDialogWithCallback.findViewById(R.id.msg_txt);
        firstBtn = mAlertDialogWithCallback.findViewById(R.id.first_btn);
        secondBtn = mAlertDialogWithCallback.findViewById(R.id.second_btn);

        //Set Data
        msgTxt.setText(message);
        firstBtn.setText(firstBtnName);
        secondBtn.setText(secondBtnName);

        firstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialogWithCallback.dismiss();
                mCallback.onOkClick();
            }
        });
        secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialogWithCallback.dismiss();
            }
        });

        mAlertDialogWithCallback.setCancelable(true);
        mAlertDialogWithCallback.setCanceledOnTouchOutside(true);
        mAlertDialogWithCallback.show();
    }

    public static void showImagePickerDialog(Context mContext,
                                             final ImagePickerCallback mImagePickerCallback) {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        mDialog = getDialog(mContext, R.layout.popup_image_selection);
        mDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationFromBottom;
        RelativeLayout mCamera, mGallery, mOutSideLay;

        mCamera = mDialog.findViewById(R.id.camera_lay);
        mGallery = mDialog.findViewById(R.id.gallery_lay);
        mOutSideLay = mDialog.findViewById(R.id.top_lay);

        mCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                mImagePickerCallback.onCameraClick();

            }
        });
        mGallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                mImagePickerCallback.onGalleryClick();
            }
        });
        mOutSideLay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDialog.dismiss();
                return false;
            }
        });
        mDialog.show();

    }

    public static void showViewImagePopup(final Context mContext, final CommonReturnCallback mCallback,
                                          Uri mUri, String mUrl) {
        if (mImageViewDialog != null && mImageViewDialog.isShowing()) {
            mImageViewDialog.dismiss();
        }
        mImageViewDialog = getDialog(mContext, R.layout.popup_image_viewer);
        mImageViewDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationFromBottom;
        final ZoomImageView mImageView;
        Button mRemoveBtn;
        Button mChangeBtn;
        Button mCloseBtn;
        mImageView = mImageViewDialog.findViewById(R.id.img_view);
        mRemoveBtn = mImageViewDialog.findViewById(R.id.remove_location_btn);
        mChangeBtn = mImageViewDialog.findViewById(R.id.change_btn);
        mCloseBtn = mImageViewDialog.findViewById(R.id.close_btn);

        if (mUri != null) {
            GlideUtils.getInstance().setZoomImageWithUri(mImageView, mUri,
                    R.drawable.default_profile_icon);
        } else if (!mUrl.isEmpty()) {
            GlideUtils.getInstance().setZoomImageWithUrl(mImageView, mUrl,
                    R.drawable.default_profile_icon);
        }
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageViewDialog.dismiss();
            }
        });
        mRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageViewDialog.dismiss();
                mCallback.isOkClick(false);
            }
        });
        mChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageViewDialog.dismiss();
                mCallback.isOkClick(true);
            }
        });
        mImageViewDialog.show();
    }

    private static Dialog getLoadingDialog(Context mContext, int mLay) {

        mDialog = getDialog(mContext, mLay);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.getWindow().setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        return mDialog;
    }

    public static void showProgress(Context context) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        try {
            mProgressDialog = getLoadingDialog(context, R.layout.progress);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setCanceledOnTouchOutside(true);
            mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    return keyCode == KeyEvent.KEYCODE_BACK;
                }
            });
            mProgressDialog.show();
        } catch (Exception e) {
            Log.e(AppConstants.TAG, e.getMessage());
        }

    }

    public static void hideProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            try {
                mProgressDialog.dismiss();
            } catch (Exception e) {
                Log.e(AppConstants.TAG, e.getMessage());
            }
        }
    }

    public static void showTimePicker(TimePickerDialog.OnTimeSetListener mTimeSetListener, BaseFragment mFragment) {
        Calendar now = Calendar.getInstance();
        mTimePickerDialog = TimePickerDialog.newInstance(mTimeSetListener,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        mTimePickerDialog.setThemeDark(false);
        mTimePickerDialog.vibrate(true);
        mTimePickerDialog.dismissOnPause(true);
        mTimePickerDialog.enableSeconds(false);
        int INTERVEL_HOUR = 1;
        int INTERVEL_MINUTES = 60;
        mTimePickerDialog.setTimeInterval(INTERVEL_HOUR, INTERVEL_MINUTES);
        mTimePickerDialog.setVersion(TimePickerDialog.Version.VERSION_2);
        mTimePickerDialog.setAccentColor(Color.parseColor("#00BFFF"));
        mTimePickerDialog.setTitle("Select Time");
        mTimePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {

            }
        });
        mTimePickerDialog.show(mFragment.getActivity().getFragmentManager(), "TimePickerDialog");
    }
}
