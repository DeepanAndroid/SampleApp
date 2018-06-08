package com.example.t2sadmin.sampleapp.utils;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.example.t2sadmin.sampleapp.R;
import com.example.t2sadmin.sampleapp.main.BaseActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;


public class DatePickerHelper {

    public static DatePickerHelper INSTANCE = new DatePickerHelper();
    private final String DATE_PICKER_DIALOG_TAG = "DatePickerDialog";

    public static DatePickerHelper getInstance() {

        return INSTANCE;
    }

    public void showDatePicker(BaseActivity mActivity, Calendar mCalendar) {

        Calendar mMaxCalendar = Calendar.getInstance();
        int mYear, mMonth, mDay;
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePickerDialog = DatePickerDialog.newInstance((DatePickerDialog.OnDateSetListener) mActivity, mYear, mMonth, mDay);

        mDatePickerDialog.setThemeDark(false);

        mDatePickerDialog.showYearPickerFirst(false);

        mDatePickerDialog.setMaxDate(mMaxCalendar);

        mDatePickerDialog.setAccentColor(ContextCompat.getColor(mActivity, R.color.header_bg));

        mDatePickerDialog.setTitle(mActivity.getResources().getString(R.string.select_date));

        mDatePickerDialog.show(mActivity.getFragmentManager(), DATE_PICKER_DIALOG_TAG);

    }


    public void showDatePicker(Fragment mFragment, Calendar mCalendar) {

        Calendar mMaxCalendar = Calendar.getInstance();
        int mYear, mMonth, mDay;
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePickerDialog = DatePickerDialog.newInstance((DatePickerDialog.OnDateSetListener) mFragment, mYear, mMonth, mDay);

        mDatePickerDialog.setThemeDark(false);

        mDatePickerDialog.showYearPickerFirst(false);

        mDatePickerDialog.setMaxDate(mMaxCalendar);

        mDatePickerDialog.setAccentColor(ContextCompat.getColor(mFragment.getActivity(), R.color.header_bg));

        mDatePickerDialog.setTitle(mFragment.getResources().getString(R.string.select_date));

        mDatePickerDialog.show(mFragment.getActivity().getFragmentManager(), DATE_PICKER_DIALOG_TAG);

    }

    public void showDatePickerWithMinimumDate(Fragment mFragment, Calendar mCalendar) {

        Calendar mMinCalendar = Calendar.getInstance();
        int mYear, mMonth, mDay;
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePickerDialog = DatePickerDialog.newInstance((DatePickerDialog.OnDateSetListener) mFragment, mYear, mMonth, mDay);

        mDatePickerDialog.setThemeDark(false);

        mDatePickerDialog.showYearPickerFirst(false);

        mDatePickerDialog.setMinDate(mMinCalendar);

        mDatePickerDialog.setAccentColor(ContextCompat.getColor(mFragment.getActivity(), R.color.header_bg));

        mDatePickerDialog.setTitle(mFragment.getResources().getString(R.string.select_date));

        mDatePickerDialog.show(mFragment.getActivity().getFragmentManager(), DATE_PICKER_DIALOG_TAG);

    }
}
