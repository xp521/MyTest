package com.example.mytest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.View;

import androidx.collection.SimpleArrayMap;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.common.ApiException;
import com.xp.tugele.haha.R;
import com.xp.tugele.haha.databinding.ActivityMain2Binding;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.LinkedList;

public class MainActivity extends Activity {
    private static final String TAG = "xue_test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMain2Binding binding = ActivityMain2Binding.inflate(getLayoutInflater());
        binding.tvName.setText("my _ test");

        setContentView(binding.getRoot());

        Test test = new Test();

        test.log();

        Intent intent = new Intent(this, TestIntentService.class);
        startService(intent);

        ClassLoader classLoader = getClassLoader();

        //Log.d("xue_test", "MainActivity:" + classLoader.toString());
        //Log.d("xue_test", "Test:" + Test.class.getClassLoader().toString());
        //Log.d("xue_test", "Context:" + Context.class.getClassLoader().toString());

        //while (classLoader != null) {
            //Log.d("xue_test", classLoader.toString());
            classLoader = classLoader.getParent();
        //}

        //getToken();
        RecyclerView recyclerView;
        testArrayMap();
        Log.d("xue_test", "defaultCharset:" + Charset.defaultCharset());
    }

    private void testFile() {
        FileInputStream fileInputStream;
    }

    private void testArrayMap() {
        try {
            @SuppressLint("SoonBlockedPrivateApi")
            Field field = Test.class.getDeclaredField("DEBUG");

            //将字段的访问权限设为true：即去除private修饰符的影响
            field.setAccessible(true);
            Log.d("xue_test", "debug1:" + field.get(null));

//去除final修饰符的影响，将字段设为可修改的
            Field modifiersField = Field.class.getDeclaredField("accessFlags");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
//把字段值设为200
            field.set(null, true);
            Log.d("xue_test", "debug2:" + field.get(null));

        } catch (NoSuchFieldException | IllegalAccessException e) {
            Log.d("xue_test", "e:" + e);
            e.printStackTrace();
        }

        ArrayMap arrayMap = new ArrayMap(4);
        arrayMap.getClass();
        for (int i = 0; i < 4; i ++) {
            arrayMap.put(i, String.valueOf(i));
        }
    }

    private void sendMessage () {
        new Thread(new Runnable() {
            @Override
            public void run() {
            }
        }).start();
    }

    public static void main(String[] args) {
        System.out.println("test");
    }

    private void getToken() {
        // 创建一个新线程
        new Thread() {
            @Override
            public void run() {
                try {
                    // 从agconnect-services.json文件中读取APP_ID
                    String appId = "1132001697346890560";

                    // 输入token标识"HCM"
                    String tokenScope = "HCM";
                    String token = HmsInstanceId.getInstance(MainActivity.this).getToken(appId, tokenScope);
                    Log.i(TAG, "get token: " + token);

                    // 判断token是否为空
                    if(!TextUtils.isEmpty(token)) {
                        sendRegTokenToServer(token);
                    }
                } catch (ApiException e) {
                    Log.e(TAG, "get token failed, " + e);
                }
            }
        }.start();
    }
    private void sendRegTokenToServer(String token) {
        Log.i(TAG, "sending token to server. token:" + token);
    }
}