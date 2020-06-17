package com.sharkz.themostbasic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sharkz.monitor.crashlog.CrashLogFileUtils;
import com.sharkz.tool.kit.TextTool;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/3/30  21:35
 * 描    述
 * 修订历史：
 * ================================================
 */
public class MainActivity extends AppCompatActivity {

    private static String TAG = "Shark";

    public TextView mTextView;

    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.tv);
        TextTool.autoMatchFontSize(mTextView, "我是谁我是谁我是谁我是谁我是谁我是谁我是谁");



      findViewById(R.id.iv).setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              //throw new NullPointerException("测试 crash_log ");
              str.substring(0,5);
          }
      });


        Log.i(TAG, "onCreate: 获取到的数据 ---> \n\n "+ CrashLogFileUtils.readLogText());



        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted(permissions -> {
                    // Storage permission are allowed.
                })
                .onDenied(permissions -> {
                    // Storage permission are not allowed.
                })
                .start();


    }

}
