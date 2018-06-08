package com.example.t2sadmin.sampleapp.utils;


import java.text.SimpleDateFormat;
import java.util.Locale;

public class AppConstants {

    public static final String TAG = "ALL_IN_ONE";
    public static final String SHARED_PREF_NAME = "My_Preference";
    public static final int REQUEST_CAMERA = 999;
    public static final int REQUEST_GALLERY = 888;
    public static final int CROP_IMG_REQ = 777;

    public static final String TERMS_URL = " http://temp1.pickzy.com/spotya/terms.html";
    public static final String PROFILE_IMAGE_NAME = "Profile";
    public static final String MULTIPART_MEDIA_TYPE = "*/*";
    public static final String MULTIPART_BODY_KEY = "files";
    public static final SimpleDateFormat COMMON_DATE_FORMAT = new SimpleDateFormat("M/d/yyyy", Locale.ENGLISH);
    public static final SimpleDateFormat SERVER_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    public static final SimpleDateFormat SERVER_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
    public static final SimpleDateFormat COMMON_DATE_TIME_FORMAT = new SimpleDateFormat("M/d/yyyy hh:mm a", Locale.ENGLISH);
    public static final SimpleDateFormat EVENT_TIME_FORMAT = new SimpleDateFormat("hha", Locale.ENGLISH);
    public static final SimpleDateFormat EVENT_DISPLAY_TIME_FORMAT = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
    public static final SimpleDateFormat SERVER_TIME_FORMAT = new SimpleDateFormat("hh:mm:ss", Locale.ENGLISH);
    public static final SimpleDateFormat EVENT_DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
    public static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("EEEE", Locale.ENGLISH);
    public static String EXTERNAL_IMAGE_DATA = "EXTERNAL_IMAGE_DATA";
    public static String WEB_LINK = "WEB_LINK";
    public static String WEB_VIEW_TITLE = "Web View";

}
