package com.sharkz.aidldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class TestAIDLActivity extends AppCompatActivity {

    private static final String TAG = "AIDL_DEMO";

    public static String TEST_STRING="我是测试的";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_a_i_d_l);
        Log.i(TAG, "onCreate: TEST_STRING="+TEST_STRING);
        TEST_STRING+="+onCreate";

        Log.i(TAG, "onCreate: STR="+getIntent().getStringExtra("STR"));
    }
}