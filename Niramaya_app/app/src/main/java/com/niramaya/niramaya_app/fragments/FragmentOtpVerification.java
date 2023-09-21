package com.niramaya.niramaya_app.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.niramaya.niramaya_app.Constants;
import com.niramaya.niramaya_app.R;
import com.niramaya.niramaya_app.customview.Button_custom;
import com.niramaya.niramaya_app.customview.EditText_custom;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;






public class FragmentOtpVerification extends BaseFragment {

    private static final String API_URL = "YOUR_SMS_GATEWAY_API_URL";
    private static final String API_KEY = "YOUR_SMS_GATEWAY_API_KEY";


    private static final String SHARED_PREF_NAME = "MyPrefs";
    private static final String PHONE_NUMBER_KEY = "phone_number";
    private static final String OTP_KEY = "otp_key";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public EditText_custom edtPh;
    public EditText_custom otpEditText;
    public Button_custom sendOtp;
    public Button_custom verifyOtp;
    public String otp;
    public String phoneNumber;
    private String message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_otp_verification, container, false);

        sendOtp = rootView.findViewById(R.id.btnSend);
        verifyOtp = rootView.findViewById(R.id.btnVerify);
        edtPh = rootView.findViewById(R.id.edtPh);
        otpEditText =  rootView.findViewById(R.id.edtOtp);

        sharedPreferences = getActivity().getSharedPreferences(Constants.PACKAGE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        sendOtp.setOnClickListener(view -> sendOtp());
        verifyOtp.setOnClickListener(view -> verifyOtp());
        return rootView;
    }
    public void sendOtp() {
        String phoneNumber = edtPh.getText().toString();


        sendOtpViaSMS(phoneNumber, otp);

        editor.putString(PHONE_NUMBER_KEY, phoneNumber);
        editor.putString(OTP_KEY, otp);
        editor.apply();
    }

    private void sendOtpViaSMS(String phoneNumber, String otp) {

        String apiKey = "YOUR_SMS_GATEWAY_API_KEY";
        String gatewayNumber = "YOUR_SMS_GATEWAY_NUMBER";

        String message = "Your OTP is: " + otp;

        boolean isSent = sendSMS(phoneNumber, gatewayNumber, apiKey, message);

        if (isSent) {
            sendOtp.setText("SMS sent");
        } else {
            sendOtp.setText("SMS sending failed!!");
        }



    }
    public void sendSMS(String phoneNumber, String message) {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String requestBody = "to=" + phoneNumber + "&message=" + message;

        RequestBody body = RequestBody.create(mediaType, requestBody);

        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseBody = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        handleSMSResponse(responseBody);
                    }
                });
            }

            private void runOnUiThread(Runnable runnable) {
            }
        });
    }

    private void handleSMSResponse(String response) {
        if (response != null) {
            if (response.contains("success")) {
                showToast("SMS sent successfully!");
            } else {
                showToast("Failed to send SMS.");
            }
        } else {
            showToast("No response received.");
        }
    }

    private void showToast(String s) {
    }


    private boolean sendSMS(String phoneNumber, String gatewayNumber, String apiKey, String message) {
        this.phoneNumber = phoneNumber;
        this.message = message;
        try {

            String apiUrl = "YOUR_SMS_GATEWAY_API_URL";

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




    private void verifyOtp() {
        String enteredOTP = otpEditText.getText().toString(); // Get user-entered OTP
        String storedPhoneNumber = sharedPreferences.getString(PHONE_NUMBER_KEY, "");
        String storedOTP = sharedPreferences.getString(OTP_KEY, "");

        if (enteredOTP.equals(storedOTP)) {
            verifyOtp.setText("OTP is correct");
        } else {
            verifyOtp.setText("Incorrect OTP!!");
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}