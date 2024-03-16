package com.example.mytest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;


/**
 * //TODO注释
 *
 * @author Created by xuepeng on 2023/4/12.
 */
public class DemoHmsMessageService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("xue_test", "DemoHmsMessageService onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    private class MyBinder extends Binder {

    }
}
