package com.example.t2sadmin.sampleapp.main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.example.t2sadmin.sampleapp.activity.RecyclerViewActivity;

import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;

public class Application extends MultiDexApplication {

    static Context mAppContext;
    private static boolean activityVisible;
    private UncaughtExceptionHandler mUncaughtExceptionHandler = new UncaughtExceptionHandler() {

        @Override
        public void uncaughtException(Thread thread, @NonNull Throwable exception) {
            exception.printStackTrace();
            android.os.Process.killProcess(android.os.Process.myPid());
            restartApp();

        }
    };

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityStopped() {
        activityVisible = false;
    }

    public static Context getAppContext() {

        return Application.mAppContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        ViewTarget.setTagId(R.id.glide_tag);
        Application.mAppContext = getApplicationContext();
        MultiDex.install(this);
        Thread.setDefaultUncaughtExceptionHandler(mUncaughtExceptionHandler);


    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    private void restartApp() {
        PendingIntent mPendingIntent = PendingIntent.getActivity(
                getAppContext(), 192837, new Intent(getAppContext(),
                        RecyclerViewActivity.class), PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mAlarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 3000,
                mPendingIntent);

        System.exit(0);
    }

    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    @SuppressWarnings("unused")
                    boolean deleteResult = deleteDir(new File(appDir, s));
                }
            }
        }

    }

    private boolean deleteDir(@Nullable File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }
}
