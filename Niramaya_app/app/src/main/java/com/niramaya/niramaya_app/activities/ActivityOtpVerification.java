package com.niramaya.niramaya_app.activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.niramaya.niramaya_app.R;

import java.io.IOException;
import okhttp3.*;

public class ActivityOtpVerification extends BaseActivity {

    private static final String API_URL = "YOUR_SMS_GATEWAY_API_URL";
    private static final String API_KEY = "YOUR_SMS_GATEWAY_API_KEY";


    private static final String SHARED_PREF_NAME = "MyPrefs";
    private static final String PHONE_NUMBER_KEY = "phone_number";
    private static final String OTP_KEY = "otp_key";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public EditText phoneNumberEditText;
    public EditText otpEditText;
    public Button sendOtp;
    public Button verifyOtp;
    public String otp;
    public String phoneNumber;
    private String gatewayNumber;
    private String apiKey;
    private String message;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);


        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        phoneNumberEditText = findViewById(R.id.edtPh);
        otpEditText = findViewById(R.id.edtOtp);
        sendOtp = findViewById(R.id.btnSend);
        verifyOtp = findViewById(R.id.btnVerify);

        sendOtp.setOnClickListener(view -> sendOtp());
        verifyOtp.setOnClickListener(view -> verifyOtp());
    }
    private void sendOtp() {
        String phoneNumber = phoneNumberEditText.getText().toString();


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



    private void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ActivityOtpVerification.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean sendSMS(String phoneNumber, String gatewayNumber, String apiKey, String message) {
        this.phoneNumber = phoneNumber;
        this.gatewayNumber = gatewayNumber;
        this.apiKey = apiKey;
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

}
