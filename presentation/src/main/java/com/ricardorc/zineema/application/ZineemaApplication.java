package com.ricardorc.zineema.application;

import android.app.Application;

import com.facebook.stetho.Stetho;

import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;

public class ZineemaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupStetho();
    }

    private void setupStetho() {
        Stetho.initializeWithDefaults(this);
    }
}
