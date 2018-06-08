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

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragmentActivity extends BaseActivity implements FragmentManager.OnBackStackChangedListener {

    @BindView(R.id.home_parent)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.header_txt)
    TextView mHeaderTxt;
    private Fragment mCurrentFragment;
    private Fragment mPreviousFragment;
    private Fragment mVisibleFragment;
    @BindView(R.id.footer_view)
    BottomNavigationView mBottomNavigationView;

    private static final String BACK_STACK_ROOT_TAG = "root_fragment";

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
        setBackStackListener();
        replaceFragment(new DashboardFragment());
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        setTitleTxt(getString(R.string.home));
                        replaceFragment(new DashboardFragment());
                        break;
                    case R.id.search:
                        setTitleTxt(getString(R.string.search));
                        replaceFragment(new SearchFragment());
                        break;
                    case R.id.notifications:
                        setTitleTxt(getString(R.string.notifications));
                        replaceFragment(new NotificationsFragment());
                        break;
                    case R.id.profile:
                        setTitleTxt(getString(R.string.profile));
                        replaceFragment(new ProfileFragment());
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

    public void addFragment(Fragment mFragment) {
        try {
            if (!isFinishing()) {
                mCurrentFragment = mFragment;
                if (mCurrentFragment != null) {
                    mCurrentFragmentTag = mFragment.getClass().getSimpleName();
                    FragmentManager mFM = getSupportFragmentManager();
                    FragmentTransaction mFT = mFM.beginTransaction();

                    boolean fragmentPopped = false;
                    try {
                        fragmentPopped = mFM
                                .popBackStackImmediate(mCurrentFragmentTag, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (mFM.findFragmentByTag(mCurrentFragmentTag) == null) {
                        //If fragment is not added in the backstack
                        if (fragmentPopped) {
                            mFT.remove(mCurrentFragment).commitAllowingStateLoss();
                            mFM.popBackStack();
                        }
                        hideCurrentFragment(mFT);

                        mFT.addToBackStack(mCurrentFragment.getClass().getSimpleName())
                                .add(R.id.content_frame, mCurrentFragment);
                        mFT.commitAllowingStateLoss();
                    }

                } else {
                    sysOut("Error in creating fragment");
                }
            } else {
                sysOut("Error in creating fragment");
            }
        } catch (Exception e) {
            sysOut("Error in creating fragment");
        }
    }

    public void replaceFragment(Fragment mFragment) {
        mCurrentFragment = mFragment;
        if (mCurrentFragment != null) {
            FragmentManager mFM = getSupportFragmentManager();
            mFM.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction mFT = mFM.beginTransaction();
            mFT.replace(R.id.content_frame, mCurrentFragment);
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
        super.onBackPressed();
        try {
            int count = getSupportFragmentManager().getBackStackEntryCount();
            if (count == 1) {
                finish();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String mCurrentFragmentTag = "", mPreviousFragmentTag = "";

    @Override
    public void onBackStackChanged() {
        try {
            FragmentManager manager = getSupportFragmentManager();
            if (manager != null) {
                int backStackEntryCount = manager.getBackStackEntryCount();
                if (backStackEntryCount > 0) {
                    mCurrentFragment = manager.findFragmentById(R.id.content_frame);
                    if (mCurrentFragment != null) {
                        mPreviousFragmentTag = mCurrentFragmentTag;
                        mCurrentFragmentTag = mCurrentFragment.getTag();

                        if (mPreviousFragmentTag == null) {
                            mPreviousFragmentTag = "";
                        }
                        if (mCurrentFragmentTag == null) {
                            mCurrentFragmentTag = "";
                        }
                        if (!mCurrentFragmentTag.equalsIgnoreCase(mPreviousFragmentTag)) {
                            mCurrentFragment.onResume();
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
