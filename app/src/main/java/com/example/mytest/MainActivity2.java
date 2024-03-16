package com.example.mytest;


import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;

import com.xp.tugele.haha.R;

public class MainActivity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Test test = (Test) getIntent().getSerializableExtra("test");
        test.log();

        View view;
        ViewConfiguration.get(getApplicationContext()).getScaledTouchSlop();

        String aid = Settings.Secure.getString(getContentResolver(), "android_id");
        Log.d("xue_test","xue_id=" + aid);
    }
}