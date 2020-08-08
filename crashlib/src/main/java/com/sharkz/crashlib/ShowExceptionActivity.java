package com.sharkz.crashlib;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/8/8  13:50
 * 描    述 崩溃日志展示 界面
 * 修订历史：
 * ================================================
 */
public class ShowExceptionActivity extends AppCompatActivity {

    private static final String KEY_CRASH_INFO = "key_crash_info";
    private TextView exceptionView;

    /**
     * 界面跳转
     *
     * @param crashInfo 日志信息
     */
    public static void showException(String crashInfo) {
        Application applicationContext = CrashManager.getApplication();
        if (applicationContext != null) {
            Intent intent = new Intent(applicationContext, ShowExceptionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(KEY_CRASH_INFO, crashInfo);
            applicationContext.startActivity(intent);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_exception);
        setListener();
        int exceptionId = getResources().getIdentifier("crash_lib_activity_show_exception_view", "id", getPackageName());
        exceptionView = findViewById(exceptionId);
        handlerIntent(getIntent(), false);
    }

    private void setListener() {
        int backBtnId = getResources().getIdentifier("crash_lib_activity_back_btn", "id", getPackageName());
        int shareBtnId = getResources().getIdentifier("crash_lib_activity_share_btn", "id", getPackageName());
        findViewById(backBtnId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(shareBtnId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrashManager.shareCrashFile(ShowExceptionActivity.this);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handlerIntent(intent, true);
    }

    private void handlerIntent(Intent intent, boolean isNew) {
        String msg = intent.getStringExtra(KEY_CRASH_INFO);
        if (msg != null) {
            if (isNew)
                exceptionView.append("\n\n\n\n\n\n");
            exceptionView.append(msg);
        }
    }
}