package com.niramaya.niramaya_app;

import java.text.SimpleDateFormat;

public class Constants
{
    public static int WIDTH = 0 , HEIGHT = 0;
    public final static String PACKAGE_NAME = "com.niramaya.niramaya_app";
    public static final String CAMERA_FILE_PROVIDER = "com.niramaya.niramaya_app.fileprovider";

    public static String sVersionCode = "0";
    public static final int PAGE_SPLASH_SCREEN = 1;
    public static final int PAGE_LOGIN = 2;
    public static final int PAGE_OTP = 3;
    public static final int PAGE_SCANNER = 4;


    public static void SOUT(String sText)
    {

        if (BuildConfig.DEBUG)
        {
            System.out.println(""+sText);
        }
    }
    public static String getTDT()
    {
        SimpleDateFormat sCurrDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        long date = System.currentTimeMillis();
        return  ""+sCurrDate.format(date);
    }

    public static String getTodayDateTimeFormat()
    {
        SimpleDateFormat sCurrDate = new SimpleDateFormat("dd MMM yyyy HH:mm");
        long date = System.currentTimeMillis();
        return  ""+sCurrDate.format(date);
    }
    public static long getTodayDateL()
    {
        SimpleDateFormat sCurrDate = new SimpleDateFormat("yyyyMMdd");
        long date = System.currentTimeMillis();
        return  Long.parseLong(""+sCurrDate.format(date));
    }
}
