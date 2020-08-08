package com.sharkz.crashlib;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/8/8  16:08
 * 描    述 用于文件分享
 * 修订历史：
 * ================================================
 */
public class CrashLogDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_log_detail);

        TextView tvDetail = findViewById(R.id.tvDetail);
        String LOG_TXT = getIntent().getStringExtra("LOG_TXT");
        tvDetail.setText(LOG_TXT);
    }
}