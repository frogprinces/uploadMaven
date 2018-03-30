package com.example.zhaoshuai.mydemocollection;

import android.app.Application;

import com.microquation.linkedme.android.LinkedME;

/**
 * Created by zs on 2017/10/19.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            //设置debug模式下打印LinkedME日志
            LinkedME.getInstance(this).setDebug();
        } else {
            LinkedME.getInstance(this);
        }
        //初始时设置为false，在配置Uri Scheme的Activity的onResume()中设置为true
        LinkedME.getInstance().setImmediate(false);


        LinkedME.getInstance().getDeviceId();

    }


}
