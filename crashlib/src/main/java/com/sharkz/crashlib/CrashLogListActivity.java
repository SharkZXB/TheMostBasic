package com.sharkz.crashlib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/8/8  15:22
 * 描    述 用于文件分享
 * 修订历史：
 * ================================================
 */
public class CrashLogListActivity extends AppCompatActivity {

    private RecyclerView rv;
    private File[] fileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_log_list);

        rv = findViewById(R.id.rv);
        fileList = CrashFileTool.getLogFileList();

        if (fileList != null && fileList.length > 0) {
            // 刷新列表
            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(RecyclerView.VERTICAL);
            rv.setLayoutManager(manager);
            CrashLogListAdapter adapter = new CrashLogListAdapter(CrashLogListActivity.this, fileList);
            rv.setAdapter(adapter);
        }

    }

}