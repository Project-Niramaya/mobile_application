package com.niramaya.niramaya_app.fragments;


import static com.niramaya.niramaya_app.Constants.PAGE_LOGIN;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niramaya.niramaya_app.Constants;
import com.niramaya.niramaya_app.R;
import com.niramaya.niramaya_app.activities.ActivityLogin;
import com.niramaya.niramaya_app.customview.Button_custom;

public class FragmentLogin extends BaseFragment
{
    private SharedPreferences sharedPref = null;

    public FragmentLogin()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        sharedPref = getActivity().getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);

        Button_custom btToast = (Button_custom) rootView.findViewById(R.id.btToast);
        btToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityLogin)getActivity()).showToast(getString(R.string.hello_test_toast));
            }
        });

        return rootView;
    }
}