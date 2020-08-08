package com.sharkz.crashlib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

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


    private File[] fileList;
    private Context context;

    public CrashLogListAdapter(Context context, File[] fileList) {
        this.context = context;
        this.fileList = fileList;
        if (fileList == null) {
            fileList = new File[0];
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
        holder.bindData(fileList[position]);
    }

    @Override
    public int getItemCount() {
        return fileList.length;
    }


    // =============================================================================================


    public class CrashLogListAdapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvItem;

        public CrashLogListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tvItem);
        }

        public void bindData(final File file) {
            tvItem.setText(file.getName());

            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    // Toast.makeText(context, "长按 分享 ", Toast.LENGTH_SHORT).show();
                    CrashShareTool.shareFile((Activity) context, file);
                    return true;
                }
            });

            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CrashLogDetailActivity.class);
                    intent.putExtra("LOG_TXT", CrashFileTool.readLogText(file));
                    context.startActivity(intent);
                }
            });
        }

    }


}
