package com.niramaya.niramaya_app.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.niramaya.niramaya_app.Constants;
import com.niramaya.niramaya_app.R;
import com.niramaya.niramaya_app.fragments.FragmentLogin;
import com.niramaya.niramaya_app.fragments.FragmentOtpVerification;
import com.niramaya.niramaya_app.fragments.FragmentScanning;
import com.niramaya.niramaya_app.fragments.FragmentSms;
import com.niramaya.niramaya_app.fragments.FragmentSplashScreen;

public class ActivityLogin extends ActivityBase
{
    private SharedPreferences sharedPref = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPref = getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
        showFragment(Constants.PAGE_SCANNING);
    }
    public void reloadFragment(int position)
    {
        Fragment fragment = null;
        switch (position)
        {
//            case Constants.PAGE_SCRATCH_COUPON:
//                fragment = new Fragment_ScratchCoupon();
//                break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }


    public void showFragment(int position)
    {
        Fragment fragment = null;
        switch (position)
        {
            case Constants.PAGE_SPLASH_SCREEN:
                fragment = new FragmentSplashScreen();
                break;
            case Constants.PAGE_LOGIN:
                fragment = new FragmentLogin();
                break;
            case Constants.PAGE_SCANNING:
                fragment = new FragmentScanning();
                break;
            case Constants.PAGE_OTP_VERIFICATION:
                fragment = new FragmentOtpVerification();
                break;
            case Constants.PAGE_SMS:
                fragment = new FragmentSms();
                break;
        }
        if (fragment != null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(fragment.getClass().getSimpleName()).commit();

        } else
        {
        }
    }
    public void goToHome()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        for(int i = 0; i < fragmentManager.getBackStackEntryCount()-1; ++i)
        {
            fragmentManager.popBackStack();
        }
    }
    @Override
    public void onBackPressed()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager.getBackStackEntryCount() >1)
        {
            super.onBackPressed();
        }
        else
        {
            finish();
        }
    }

}
