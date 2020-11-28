package com.sharkz.aidldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "AIDL_DEMO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void aidl(View view) {
        // startActivity(new Intent(MainActivity.this, AIDLActivity.class));

        Intent intent = new Intent(MainActivity.this, TestAIDLActivity.class);
        intent.putExtra("STR", "123");
        startActivity(intent);
    }

    public void messenger(View view) {
        startActivity(new Intent(MainActivity.this, MessengerActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: TEST_STRING=" + TestAIDLActivity.TEST_STRING);
    }
}
