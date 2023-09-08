package com.niramaya.niramaya_app.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.niramaya.niramaya_app.Constants;
import com.niramaya.niramaya_app.R;
import com.niramaya.niramaya_app.customview.Button_custom;
import com.niramaya.niramaya_app.customview.TextView_custom;


public class ActivityBase extends AppCompatActivity
{
    final int iImages[] = new int[]{R.drawable.a_1,R.drawable.a_2,R.drawable.a_3,R.drawable.a_4,R.drawable.a_5};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Constants.HEIGHT = displayMetrics.heightPixels;
        Constants.WIDTH = displayMetrics.widthPixels;

        try
        {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            Constants.sVersionCode = ""+pInfo.versionCode;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    private Dialog custom_loading = null;
    public void showProgress(boolean blCancelable)
    {

        try
        {

            if(custom_loading != null && custom_loading.isShowing())
            {
                hideProgress();
            }

            custom_loading = new Dialog(this);
            custom_loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
            custom_loading.setContentView(R.layout.dialog_custom_loading);
//            custom_loading.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            custom_loading.setCancelable(blCancelable);

            final ImageView image = (ImageView) custom_loading.findViewById(R.id.image);
            custom_loading.show();

            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        int iTimerCounter = 0,iImageCounter=0;
                        while (true)
                        {
                            Thread.sleep(100);
                            if(iImageCounter < iImages.length)
                            {
                                image.setImageResource(iImages[iImageCounter]);
                            }
                            else
                            {
                                iTimerCounter = 0;iImageCounter=-1;
                            }
                            iImageCounter = iImageCounter + 1;
                            iTimerCounter = iTimerCounter +200;
                        }
                    }
                    catch (Exception e)
                    {

                    }
                }
            }).start();


        }catch (Exception e)
        {

        }

    }
    public void hideProgress()
    {
        try
        {
            if(custom_loading !=null )
            {
                if(custom_loading.isShowing())
                {
                    custom_loading.dismiss();
                    custom_loading = null;
                }
            }
        }catch (Exception e)
        {

        }
    }
    public void showToast(String sText)
    {
        LayoutInflater li = getLayoutInflater();
        View viewToast = li.inflate(R.layout.custom_toast,null);
        Toast toast = Toast.makeText(this,""+sText, Toast.LENGTH_LONG);
        TextView_custom tvToast = (TextView_custom) viewToast.findViewById(R.id.tvToast);
        tvToast.setText(""+sText);
//        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.setView(viewToast);
        toast.show();
    }
    public void showNetworkError()
    {
        String sText = getString(R.string.network_is_unreachable_);

        final Dialog dialogChoice = new Dialog(this);
        dialogChoice.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogChoice.setContentView(R.layout.dialog_network_error);
        dialogChoice.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogChoice.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogChoice.getWindow().setGravity(Gravity.CENTER);
        dialogChoice.show();
        dialogChoice.setCancelable(false);
        TextView tvToast = (TextView) dialogChoice.findViewById(R.id.tvToast);
        tvToast.setText(""+sText);
        final Button_custom btOk = (Button_custom) dialogChoice.findViewById(R.id.btOk);
        btOk.setOnClickListener(v -> dialogChoice.dismiss());
    }

    public void hideSoftKeyboard()
    {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText())
        {
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),0);
        }
    }
}