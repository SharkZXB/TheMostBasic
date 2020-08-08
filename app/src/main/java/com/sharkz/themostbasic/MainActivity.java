package com.sharkz.themostbasic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sharkz.crashlib.CrashManager;
import com.sharkz.monitor.LoggerTool;
import com.sharkz.monitor.crashlog.CrashLogFileUtils;
import com.sharkz.monitor.crashlog.SharkCrashLog;
import com.sharkz.tool.kit.TextTool;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

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

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                throw new NullPointerException(" 测试 空指针 ");

//                try {
//                    throw new NullPointerException(" 测试 空指针 ");
//                } catch (Exception e) {
//                   // Log.i(TAG, "onClick: collectCrashInfo"+collectCrashInfo(e));
                    // LoggerTool.logDEBUG(collectCrashInfo(e));
//                }
            }
        });


        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CrashManager.jump2CrashLogListActivity(MainActivity.this);
            }
        });

    }



    /**
     * 获取异常信息
     */
    private String collectCrashInfo(Exception ex) {
        if (ex == null) return "";
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable throwable = ex.getCause();
        while (throwable != null) {
            throwable.printStackTrace(printWriter);
            throwable = throwable.getCause();//逐级获取错误信息
        }
        String crashInfo = writer.toString();
        printWriter.close();
        return crashInfo;
    }

}
