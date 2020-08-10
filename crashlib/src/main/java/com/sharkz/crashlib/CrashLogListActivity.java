package com.sharkz.crashlib;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    private List<File> fileList = new ArrayList<>();
    private TextView tvEdit;
    private Group group_CheckAll_Delete;

    private CrashLogListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_log_list);

        RecyclerView rv = findViewById(R.id.rv);
        tvEdit = findViewById(R.id.tvEdit);
        group_CheckAll_Delete = findViewById(R.id.group_CheckAll_Delete);

        //添加数据
        fileList.addAll(CrashFileTool.getLogFileListV2());

        // 刷新列表
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new CrashLogListAdapter(CrashLogListActivity.this, fileList);
        rv.setAdapter(adapter);

    }


    // =============================================================================================


    /**
     * 编辑
     */
    public void onClickEdit(View view) {
        if (fileList.size() < 1) {
            Toast.makeText(this, "暂无数据可操作", Toast.LENGTH_SHORT).show();
            return;
        }

        if (adapter.setEdit()) {
            tvEdit.setText("取消");
            group_CheckAll_Delete.setVisibility(View.VISIBLE);
        } else {
            tvEdit.setText("编辑");
            group_CheckAll_Delete.setVisibility(View.GONE);
            adapter.cancelCheckAll();
        }
    }

    /**
     * 全选
     */
    public void onCLickCheckAll(View view) {
        adapter.checkAll();
    }

    /**
     * 删除
     */
    public void onCLickDelete(View view) {
        List<File> items = adapter.getCheckedItem();
        if (items.size() < 1) {
            Toast.makeText(CrashLogListActivity.this, "至少选中一个数据", Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i = 0; i < items.size(); i++) {
            CrashFileTool.deleteFile(items.get(i));
        }

        // 操作 隐藏
        onClickEdit(null);

        // 刷新列表
        fileList.clear();
        fileList.addAll(CrashFileTool.getLogFileListV2());
        adapter.notifyDataSetChanged();
    }

}