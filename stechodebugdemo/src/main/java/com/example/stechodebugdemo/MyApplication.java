package com.example.stechodebugdemo;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * User: WangKai(123940232@qq.com)
 * 2015-11-17 21:52
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initStecho();

    }

    private void initStecho() {
//        Stetho.newInitializerBuilder(this);
        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(
                    Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(
                    Stetho.defaultInspectorModulesProvider(this))
                .build());

    }
}
