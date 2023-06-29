package com.niramaya.niramaya_app.serverapi;

import com.niramaya.niramaya_app.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient
{
    private static Retrofit retrofit = null;

    public static Retrofit getUatClient(String URL)
    {
        final OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
        return retrofit;
    }
    public static Retrofit getClient(String URL)
    {
        URL = BuildConfig.API_BASE +URL;
        return getUatClient(URL);
    }
    public static Retrofit getLiveClient(String URL)
    {
        CertificatePinner certificatePinner = new CertificatePinner.Builder()
                .add("domain", "sha256/8+PR0N")
                .build();

//        CertificatePinner certificatePinner = new CertificatePinner.Builder()
//                .add("eservices-test.nsdl.com", "sha256/980Ionqp3wkYtN9SZVgMzuWQzJta1nfxNPwTem1X0uc=")
//                .build();


        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .certificatePinner(certificatePinner)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
        return retrofit;
    }
}