package com.niramaya.niramaya_app.fragments;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.niramaya.niramaya_app.Constants;
import com.niramaya.niramaya_app.R;
import com.niramaya.niramaya_app.activities.Activity_CapturePortrait;
import com.niramaya.niramaya_app.customview.Button_custom;
import com.niramaya.niramaya_app.customview.TextView_custom;

public class FragmentScanning extends BaseFragment
{
    private SharedPreferences sharedPref = null;
    private Button_custom btScan;
    private TextView_custom tvResult;
    private void scanCode()
    {

    }
    private void scanCode()
    {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLaucher.launch(options);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_scanning, container, false);
        sharedPref = getActivity().getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);

        tvResult = (TextView_custom) rootView.findViewById(R.id.tvResult);

        btScan = (Button_custom) rootView.findViewById(R.id.btScan);
        btScan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startBarcodeScan();
            }
        });


        return rootView;
    }

    private void startBarcodeScan()
    {

        Dexter.withContext(getActivity())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response)
                    {
                        ScanOptions options = new ScanOptions();
                        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
                        options.setBeepEnabled(false);
                        options.setBarcodeImageEnabled(true);
                        options.setOrientationLocked(true);
                        options.setCaptureActivity(Activity_CapturePortrait.class);
                        barcodeLauncher.launch(options);
                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response)
                    {

                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token)
                    {

                    }
                }).check();
    }
    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null)
                {
                }
                else
                {
                    String sLastScanned = result.getContents();
                    String sContents = ""+sLastScanned.replaceAll("\n","");
                    tvResult.setText(""+sContents);
                }
            });
}