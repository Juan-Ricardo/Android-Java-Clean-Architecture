package com.ricardorc.datasource.persistance.network.interceptor;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ThemovieInterceptor implements Interceptor {

    private Context context;

    public ThemovieInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        //Request.Builder builder = request.newBuilder();

        HttpUrl url = chain.request().url();

        Log.v("sinemalog: ", "" + request.tag());
        Log.v("sinemalog: ", "" + request.method());
        Log.v("sinemalog: ", "" + request.body());
        Log.v("sinemalog: ", "" + request.isHttps());
        Log.v("sinemalog: ", "" + request.url().scheme());
        Log.v("sinemalog: ", "" + request.url().host());
        Log.v("sinemalog: ", "" + request.url().query());



        //request.url().newBuilder().addEncodedPathSegment();

        /*HttpUrl httpUrl = request.url()
                .newBuilder()
                .addQueryParameter("", "")
                .build();
        request.newBuilder().url(httpUrl).build();

        builder.addHeader("", "");
        request = builder.build();*/


        Response response = chain.proceed(request);

        if (response.isSuccessful()) {
            return response;
            //return errorResponse(response);
        } else {
            return errorResponse(response);
        }
    }

    private void isSuccessfulResponse(Response response) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code", 200);
            jsonObject.put("status", "OK");
            jsonObject.put("message", new JSONObject(response.body().string()));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Response errorResponse(Response response) {
        JSONObject jsonObject = new JSONObject();
        MediaType contentType = response.body().contentType();
        ResponseBody body = ResponseBody.create(contentType, jsonObject.toString());
        try {
            jsonObject.put("code", 404);
            jsonObject.put("status", "ERROR");
            jsonObject.put("message", new JSONObject(response.body().string()));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.newBuilder().body(body).build();
    }
}
