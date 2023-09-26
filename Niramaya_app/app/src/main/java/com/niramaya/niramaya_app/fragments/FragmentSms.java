package com.niramaya.niramaya_app.fragments;


import android.os.Bundle;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niramaya.niramaya_app.R;
import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;


import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;


import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
public class FragmentSms extends BaseFragment {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private ArrayAdapter<String> adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sms, container, false);

        Button retrieveButton = rootView.findViewById(R.id.retrieveButton);
        ListView smsListView = rootView.findViewById(R.id.smsListView);

        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1);
        smsListView.setAdapter(adapter);

        retrieveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_SMS}, PERMISSION_REQUEST_CODE);
                } else {
                    retrieveSMSMessages();
                }
            }
        });

        return rootView;
    }

    private void retrieveSMSMessages() {
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                adapter.add("From: " + address + "\nMessage: " + body);
            } while (cursor.moveToNext());

            cursor.close();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                retrieveSMSMessages();
            } else {

                showPermissionDeniedDialog();
            }
        }
    }

    private void showPermissionDeniedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("This app requires permission to read SMS messages.")
                .setTitle("Permission Required")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // You may take additional action here if needed
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle user cancelation if needed
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

