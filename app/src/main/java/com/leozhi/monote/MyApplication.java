package com.leozhi.monote;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * @author leozhi
 * @date 20-9-15
 */
public class MyApplication extends Application {
    /**
     * 此处Context是Application Context，并不会造成内存泄漏
     * 假如为静态的Activity Context则会造成内存泄漏
     */
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
