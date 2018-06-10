package com.example.t2sadmin.sampleapp.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.t2sadmin.sampleapp.R;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    protected void setupUI(View view) {

        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard();
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

    protected void hideSoftKeyboard() {
        try {
            InputMethodManager mInputMethodManager = (InputMethodManager) getActivity()
                    .getSystemService(INPUT_METHOD_SERVICE);

            if (getActivity().getCurrentFocus() != null
                    && getActivity().getCurrentFocus().getWindowToken() != null) {
                mInputMethodManager.hideSoftInputFromWindow(getActivity()
                        .getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onRefreshFragment(){

    }

    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    public void sysOut(String msg) {
//        System.out.println(getString(R.string.app_name) + " " + msg);
        Log.e(getString(R.string.app_name), msg);
    }

    public void showSnackBar(CoordinatorLayout mLayout, String msg) {
        Snackbar snackbar = Snackbar
                .make(mLayout, msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

}
