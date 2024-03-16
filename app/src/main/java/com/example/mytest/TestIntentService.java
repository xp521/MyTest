package com.example.mytest;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentSender;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * //TODO注释
 *
 * @author Created by xuepeng on 2022/12/7.
 */
public class TestIntentService extends IntentService {

    public TestIntentService() {
        super("test");
    }

    @Override
    public void onDestroy() {
        Log.d("xue_test", "onDestroy thread " + Thread.currentThread().getName());

        super.onDestroy();
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        Log.d("xue_test", "onStart thread " + Thread.currentThread().getName());

        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d("xue_test", "onStartCommand thread " + Thread.currentThread().getName());

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("xue_test", "onHandleIntent thread " + Thread.currentThread().getName());
    }
}
