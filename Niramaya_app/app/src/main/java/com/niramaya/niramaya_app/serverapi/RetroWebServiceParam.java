package com.niramaya.niramaya_app.serverapi;

import static com.niramaya.niramaya_app.Constants.SOUT;

import android.app.Activity;

import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class RetroWebServiceParam
{
    private Activity context;
    public RetroWebServiceParam(Activity context)
    {
        this.context = context;
    }
    public Call<ResponseBody> doLogin(String sMobileNo,String sVersion,String sFCMKey)
    {
        String sJSON = "";
        try
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("strMobileNo1",sMobileNo);
            jsonObject.put("AppVersion",sVersion);
            jsonObject.put("FCMKey",sFCMKey);

            sJSON = jsonObject.toString();
        }catch (Exception e)
        {

        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),sJSON);
        RetroApis apiInterface = APIClient.getClient("MobileLogin/").create(RetroApis.class);
        Call<ResponseBody> call = apiInterface.doLogin(body);
        SOUT("API:"+call.request().url());
        SOUT("REQ:"+sJSON);
        return call;
    }

    public Call<ResponseBody> getUser()
    {
        RetroApis apiInterface = APIClient.getClient("User/").create(RetroApis.class);
        Call<ResponseBody> call = apiInterface.getUser();
        SOUT("API:"+call.request().url());
        return call;
    }
}
