package com.ricardorc.datasource.persistance.network.client.themovie;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.ricardorc.datasource.BuildConfig;
import com.ricardorc.datasource.persistance.network.client.base.ApiClient;
import com.ricardorc.datasource.persistance.network.interceptor.ThemovieInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThemovieClient extends ApiClient {
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;
    private HttpLoggingInterceptor logging;
    private static ThemovieClient themovieClient;

    public ThemovieClient(Context context) {
        logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = getOkHttpClient(context);
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.addInterceptor(logging);
        //builder.addInterceptor(new ChuckInterceptor(context));
        builder.addInterceptor(new ThemovieInterceptor(context));
        builder.addNetworkInterceptor(new StethoInterceptor());
        okHttpClient = builder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static ThemovieClient getInstance(Context context) {
        if (themovieClient == null) {
            themovieClient = new ThemovieClient(context);
        }
        return themovieClient;
    }

    @Override
    public <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
