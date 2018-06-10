package com.example.t2sadmin.sampleapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.t2sadmin.sampleapp.R;
import com.example.t2sadmin.sampleapp.fragment.DashboardFragment;
import com.example.t2sadmin.sampleapp.fragment.NotificationsFragment;
import com.example.t2sadmin.sampleapp.fragment.ProfileFragment;
import com.example.t2sadmin.sampleapp.fragment.SearchFragment;
import com.example.t2sadmin.sampleapp.main.BaseActivity;
import com.example.t2sadmin.sampleapp.main.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragmentActivity extends BaseActivity implements FragmentManager.OnBackStackChangedListener {

    @BindView(R.id.home_parent)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.header_txt)
    TextView mHeaderTxt;
    private BaseFragment mCurrentFragment;
    private BaseFragment mPreviousFragment;
    private BaseFragment mVisibleFragment;
    @BindView(R.id.footer_view)
    BottomNavigationView mBottomNavigationView;

    private static final String BACK_STACK_ROOT_TAG = "root_fragment";
    private FragmentManager mFM;
//    private FragmentTransaction mFT;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_home_screen);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mHeaderTxt.setVisibility(View.VISIBLE);
        setTitleTxt(getString(R.string.home));

        mFM = getSupportFragmentManager();


        setBackStackListener();
        addFragment(new DashboardFragment());
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        setTitleTxt(getString(R.string.home));
                        addFragment(new DashboardFragment());
                        break;
                    case R.id.search:
                        setTitleTxt(getString(R.string.search));
                        addFragment(new SearchFragment());
                        break;
                    case R.id.notifications:
                        setTitleTxt(getString(R.string.notifications));
                        addFragment(new NotificationsFragment());
                        break;
                    case R.id.profile:
                        setTitleTxt(getString(R.string.profile));
                        addFragment(new ProfileFragment());
                        break;
                }
                return true;
            }
        });

        mBottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

            }
        });

    }

    public void setTitleTxt(String sTitle) {
        mHeaderTxt.setText(sTitle);
    }

    public void addFragment1(BaseFragment mFragment) {
        try {
            if (!isFinishing()) {
                mCurrentFragment = mFragment;
                if (mCurrentFragment != null) {
                    FragmentTransaction mFT = mFM.beginTransaction();
                    mCurrentFragmentTag = mFragment.getClass().getSimpleName();

                    boolean fragmentPopped = false;
                    try {
                        fragmentPopped = mFM
                                .popBackStackImmediate(mCurrentFragmentTag, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    sysOut("FRAG: POPPED" + fragmentPopped);
                    if (mFM.findFragmentByTag(mCurrentFragmentTag) == null) {
                        //If fragment is not added in the backstack
                        if (fragmentPopped) {
                            sysOut("FRAG:  ON REMOVE");
                            mFT.remove(mCurrentFragment).commitAllowingStateLoss();
                            mFM.popBackStack();
                        }
                        hideCurrentFragment(mFT);
                        sysOut("FRAG:  ON ADD");
                        sysOut("FRAG: INSIDE  " + mCurrentFragmentTag);
                        mFT.addToBackStack(mCurrentFragment.getClass().getSimpleName())
                                .add(R.id.content_frame, mCurrentFragment);
                        mFT.commitAllowingStateLoss();

                        return;
                    }
                    sysOut("FRAG: OUTSIDE  " + mCurrentFragmentTag);

                } else {
                    sysOut("Error in creating null fragment");
                }
            } else {
                sysOut("Error in creating withoutactivity fragment");
            }
        } catch (Exception e) {
            sysOut("ERROR" + e.getMessage());
        }
    }

    public void addFragment(BaseFragment mFragment) {
        try {
            if (!isFinishing()) {
                mCurrentFragment = mFragment;
                if (mCurrentFragment != null) {
                    FragmentTransaction mFT = mFM.beginTransaction();
                    mCurrentFragmentTag = mFragment.getClass().getSimpleName();

                    List<Fragment> mList = mFM.getFragments();
                    for (int i = 0; i < mList.size(); i++) {
                        if (mList.get(i).getClass().getSimpleName().equals(mCurrentFragmentTag)) {
                            mFT.remove(mCurrentFragment);
                            mList.remove(i);
                            mFM.popBackStack();
                            break;
                        }
                    }
                    sysOut("FRAG NEW ADD " + mCurrentFragmentTag);
                    mFT.addToBackStack(mCurrentFragment.getClass().getSimpleName())
                            .add(R.id.content_frame, mCurrentFragment);
                    mFT.commitAllowingStateLoss();
                } else {
                    sysOut("Error in creating null fragment");
                }
            } else {
                sysOut("Error in creating withoutactivity fragment");
            }
        } catch (Exception e) {
            sysOut("ERROR" + e.getMessage());
        }
    }

    public void replaceFragment(BaseFragment mFragment) {
        mCurrentFragment = mFragment;
        if (mCurrentFragment != null) {
            FragmentTransaction mFT = mFM.beginTransaction();
            mFM.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            mFT.replace(R.id.content_frame, mCurrentFragment);
            mCurrentFragmentTag = mFragment.getClass().getSimpleName();
            sysOut("FRAG:  ON REPLACE");
            sysOut("FRAG: " + mCurrentFragmentTag);
            mFT.commitAllowingStateLoss();
        } else {
            sysOut("Error in creating fragment");
        }
    }

    private void hideCurrentFragment(FragmentTransaction mFT) {
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (visibleFragment != null)
            mFT.hide(visibleFragment);
    }

    private void setBackStackListener() {
        try {
            getSupportFragmentManager().addOnBackStackChangedListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        try {
            int count = mFM.getBackStackEntryCount();
            sysOut("FRAG: COUNT: " + count);
            if (count == 0) {
//                finish();
                showSnackBar(mCoordinatorLayout, "LastFragment");
            } else {

//                FragmentManager.BackStackEntry mBackStackEntry = getSupportFragmentManager().getBackStackEntryAt(3);
                List<Fragment> mList = mFM.getFragments();
                Fragment fragment = mList.get(count - 1);
                sysOut("FRAG: BACKSTAC SIZE " + mList);
                sysOut("FRAG: BACKSTAK NAME " + fragment.getClass().getName());
                FragmentTransaction mFT = mFM.beginTransaction();
                if (fragment != null)
//                    mFT.show(fragment);
                    fragment.onResume();

                mFM.popBackStack();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String mCurrentFragmentTag = "", mPreviousFragmentTag = "";

    @Override
    public void onBackStackChanged() {
//        try {
//            if (mFM != null) {
//                FragmentTransaction mFT = mFM.beginTransaction();
//                int backStackEntryCount = mFM.getBackStackEntryCount();
//                if (backStackEntryCount > 0) {
//                    mCurrentFragment = (BaseFragment) mFM.findFragmentById(R.id.content_frame);
//                    if (mCurrentFragment != null) {
//                        mPreviousFragmentTag = mCurrentFragmentTag;
//                        mCurrentFragmentTag = mCurrentFragment.getTag();
//
//                        if (mPreviousFragmentTag == null) {
//                            mPreviousFragmentTag = "";
//                        }
//                        if (mCurrentFragmentTag == null) {
//                            mCurrentFragmentTag = "";
//                        }
//                        if (!mCurrentFragmentTag.equalsIgnoreCase(mPreviousFragmentTag)) {
//                            sysOut("FRAG:  ON REFRESH");
//                            mFT.show(mCurrentFragment);
////                            mCurrentFragment.onRefreshFragment();
//                        }
//                    }
//
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
