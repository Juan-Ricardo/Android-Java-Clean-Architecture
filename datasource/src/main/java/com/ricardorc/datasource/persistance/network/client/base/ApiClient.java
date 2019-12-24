package com.ricardorc.datasource.persistance.network.client.base;

import android.content.Context;

import okhttp3.OkHttpClient;

public abstract class ApiClient {
    public abstract <S> S createService(Class<S> serviceClass);
    public OkHttpClient.Builder getOkHttpClient(Context context) {
        return new OkHttpClient.Builder();
    }
}
