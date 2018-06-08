package com.example.t2sadmin.sampleapp.main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.t2sadmin.sampleapp.R;
import com.example.t2sadmin.sampleapp.interfaces.CommonCallback;
import com.example.t2sadmin.sampleapp.interfaces.PermissionCallback;
import com.example.t2sadmin.sampleapp.utils.DialogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BaseActivity extends AppCompatActivity {
    public static AppCompatActivity mActivity;
    public static Dialog mNotifyDialog;
    public List<String> listPermissionsNeeded;
    CommonCallback mConfirmCallback = new CommonCallback() {
        @Override
        public void onOkClick() {
            Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
            myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
            myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(myAppSettings, 168);
        }

    };
    private int permsRequestCode = 200;
    private boolean isUpdateSessionAPI = false;
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }
    private PermissionCallback mPermissionCallback = null;
    CommonCallback mAlertCallback = new CommonCallback() {
        @Override
        public void onOkClick() {
            isPermission(mPermissionCallback, listPermissionsNeeded);

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mActivity = this;
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Lato-Regular.otf").build());
    }

    /**
     * Touch and Hide keyboard
     *
     * @param view
     */
    public void setupUI(View view) {

        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(mActivity);
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View mInnerView = ((ViewGroup) view).getChildAt(i);
                setupUI(mInnerView);
            }
        }
    }

    public void hideSoftKeyboard(AppCompatActivity mActivity) {
        try {
            if (mActivity != null && !mActivity.isFinishing()) {
                InputMethodManager mInputMethodManager = (InputMethodManager) mActivity
                        .getSystemService(INPUT_METHOD_SERVICE);

                if (mActivity.getCurrentFocus() != null
                        && mActivity.getCurrentFocus().getWindowToken() != null) {
                    mInputMethodManager.hideSoftInputFromWindow(mActivity
                            .getCurrentFocus().getWindowToken(), 0);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showSoftKeyboard(EditText mEditText) {
        mEditText.requestFocus();
        mEditText.setCursorVisible(true);
        InputMethodManager imms = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imms.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);

    }

    /**
     * Move to Next Screen
     *
     * @param clazz
     */
    public void launchScreen(Class<?> clazz) {
        Intent mIntent = new Intent(mActivity, clazz);
        mActivity.startActivity(mIntent);
//        mActivity.overridePendingTransition(R.anim.slide_in_right,
//                R.anim.slide_out_left);
        mActivity.finish();
    }

    public void nextScreen(Class<?> clazz) {
        Intent mIntent = new Intent(mActivity, clazz);
        mActivity.startActivity(mIntent);
    }

    public void previousScreen(Class<?> clazz) {
        Intent mIntent = new Intent(mActivity, clazz);
        mActivity.startActivity(mIntent);
        mActivity.finish();
    }

    public void finishScreen() {
        mActivity.finish();
    }

    public void sysOut(String msg) {
//        System.out.println(getString(R.string.app_name) + " " + msg);
        Log.e(getString(R.string.app_name), msg);
    }

    public void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public void showSnackBar(View mView, String msg) {
        Snackbar snackbar = Snackbar
                .make(mView, msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActivity = this;
    }

    public boolean isPermission(PermissionCallback permissionCallback, List<String> camera) {
        mPermissionCallback = permissionCallback;
        listPermissionsNeeded = new ArrayList<>();
        listPermissionsNeeded.addAll(camera);

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), permsRequestCode);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 200: {
                Map<String, Integer> perms = new HashMap<>();
                for (int i = 0; i < listPermissionsNeeded.size(); i++) {
                    perms.put(listPermissionsNeeded.get(i), PackageManager.PERMISSION_GRANTED);
                }
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    for (int j = 0; j < listPermissionsNeeded.size(); j++) {
                        if (perms.get(listPermissionsNeeded.get(j)) == PackageManager.PERMISSION_GRANTED) {
                            if (j == listPermissionsNeeded.size() - 1) {
                                if (mPermissionCallback != null) {
                                    mPermissionCallback.permissionOkClick();
                                }
                            }
                        } else {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(this, listPermissionsNeeded.get(j))) {
                                //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
                                DialogManager.showAlertDialogWithCallback(this, mAlertCallback,
                                        "Permission must be required for this app");
                                break;
                            } else {
                                //permission is denied (and never ask again is  checked)
                                //shouldShowRequestPermissionRationale will return false
                                if (perms.get(listPermissionsNeeded.get(j)) == PackageManager.PERMISSION_DENIED) {
                                    DialogManager.showAlertDialogWithCallback(this, mConfirmCallback,
                                            getString(R.string.alert_settings));
                                    break;
                                } else {
                                    if (j == listPermissionsNeeded.size() - 1) {
                                        if (mPermissionCallback != null) {
                                            mPermissionCallback.permissionOkClick();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
