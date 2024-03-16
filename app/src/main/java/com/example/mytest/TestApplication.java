package com.example.mytest;

import android.app.Application;
import android.util.Log;
import android.view.Choreographer;

/**
 * //TODO注释
 *
 * @author Created by xuepeng on 2023/6/15.
 */
public class TestApplication extends Application {
    private long last;
    @Override
    public void onCreate() {
        super.onCreate();

        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                Log.d("xue_test", "doFrame on " + frameTimeNanos + " cost " + (frameTimeNanos - last));
                last = frameTimeNanos;

                Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }
}
