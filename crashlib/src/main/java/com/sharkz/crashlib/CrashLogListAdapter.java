package com.sharkz.crashlib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/8/8  15:43
 * 描    述
 * 修订历史：
 * ================================================
 */
public class CrashLogListAdapter extends RecyclerView.Adapter<CrashLogListAdapter.CrashLogListAdapterViewHolder> {


    private List<File> fileList;
    private Context context;
    private boolean isEdit; // 编辑模式
    private Map<Integer, Boolean> checkMap = new HashMap<>();
    private boolean isCheckAll; // 当前是否全选

    public CrashLogListAdapter(Context context, List<File> fileList) {
        this.context = context;
        this.fileList = fileList;
        if (fileList == null) {
            fileList = new ArrayList<>();
        }
        for (int i = 0; i < fileList.size(); i++) {
            checkMap.put(i, false);
        }
    }

    @NonNull
    @Override
    public CrashLogListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_crash_log_list_activity, parent, false);
        return new CrashLogListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CrashLogListAdapterViewHolder holder, int position) {
        holder.bindData(fileList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }


    // =============================================================================================

    /**
     * 编辑模式
     */
    public boolean setEdit() {
        notifyDataSetChanged();
        isEdit = !isEdit;
        return isEdit;
    }

    /**
     * 全选
     */
    public void checkAll() {
        if (isCheckAll) {
            for (int i = 0; i < fileList.size(); i++) {
                checkMap.put(i, false);
            }
        } else {
            for (int i = 0; i < fileList.size(); i++) {
                checkMap.put(i, true);
            }
        }
        isCheckAll = !isCheckAll;
        notifyDataSetChanged();
    }

    /**
     * 取消全选 重置数据
     */
    public void cancelCheckAll() {
        for (int i = 0; i < fileList.size(); i++) {
            checkMap.put(i, false);
        }
        notifyDataSetChanged();
    }

    /**
     * 获取选中的 Item
     */
    public List<File> getCheckedItem() {
        List<File> list = new ArrayList<>();
        for (Map.Entry<Integer, Boolean> entry : checkMap.entrySet()) {
            if (entry.getValue()) {
                list.add(fileList.get(entry.getKey()));
            }
        }
        return list;
    }


    // =============================================================================================


    public class CrashLogListAdapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvItem;
        private final AppCompatCheckBox checkBox;

        public CrashLogListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tvItem);
            checkBox = itemView.findViewById(R.id.checkBox);
        }

        public void bindData(final File file, final int position) {
            tvItem.setText(file.getName());

            // 长按 --> 分享
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    // Toast.makeText(context, "长按 分享 ", Toast.LENGTH_SHORT).show();
                    CrashShareTool.shareFile((Activity) context, file);
                    return true;
                }
            });

            // 点击 --> 查看详情
            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CrashLogDetailActivity.class);
                    intent.putExtra("LOG_TXT", CrashFileTool.readLogText(file));
                    context.startActivity(intent);
                }
            });

            // 编辑模式
            if (isEdit) {
                checkBox.setVisibility(View.VISIBLE);
                // 编辑模式下才显示
                if (checkMap.get(position)) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        checkMap.put(position, b);
                        if (!b) {
                            isCheckAll = false;
                        }
                    }
                });

            } else {
                checkBox.setVisibility(View.GONE);
            }

        }

    }


}
