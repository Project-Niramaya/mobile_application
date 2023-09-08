package com.niramaya.niramaya_app.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niramaya.niramaya_app.Constants;
import com.niramaya.niramaya_app.R;

public class FragmentHome extends BaseFragment
{
    private SharedPreferences sharedPref = null;

    public FragmentHome()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        sharedPref = getActivity().getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);



        return rootView;
    }
}
