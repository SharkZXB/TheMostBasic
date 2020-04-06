package com.sharkz.themostbasic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sharkz.swipebacklayout.SwipeBackLayoutActivity;

public class TestSwipeBackLayoutActivity extends SwipeBackLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_swipe_back_layout);
    }

}
