package com.sharkz.crashlib;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/8/8  14:55
 * 描    述 Crash 日志分享
 * 修订历史：
 * ================================================
 */
public class CrashShareTool {


    // 压缩包 保存路径
    private static final String LOG_ZIP_FILE_PATH = CrashLogConfig.getInstance().getLogFilePath() + File.separator + "crash_log.zip";

    /**
     * 分享 崩溃日子信息
     *
     * @param activity
     */
    public static void shareCrashFile(Activity activity) {
        // 日志的路径
        File logPath = new File(CrashLogConfig.getInstance().getLogFilePath());
        if (!logPath.exists() || logPath.listFiles() == null || logPath.listFiles().length == 0) {
            Toast.makeText(activity, "没有崩溃日志可以反馈", Toast.LENGTH_SHORT).show();
            return;
        }
        if (logPath.listFiles().length == 1) {//只有一个崩溃文件则直接打开
            Uri uri = CrashFileTool.getUri(activity, logPath.listFiles()[0]);
            shareFile(activity, uri, "text/plain");
        } else {//多个文件的话，压缩之后发送压缩包
            CrashFileTool.zipFiles(logPath.listFiles(), LOG_ZIP_FILE_PATH);
            Uri uri = CrashFileTool.getUri(activity, new File(LOG_ZIP_FILE_PATH));
            shareFile(activity, uri, "application/zip");
        }
    }


    /**
     * 分享文件 调用系统
     *
     * @param fileUri  文件uri
     * @param fileType 文件类型（具体文件类型可参见本类下方注释）
     */
    private static void shareFile(Activity activity, Uri fileUri, String fileType) {
        //表示要创建一个发送指定内容的隐式意图
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //读取权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //指定发送的内容
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
        //intent.putExtra(Intent.EXTRA_STREAM,uri);
        //指定发送内容的类型
        intent.setType(fileType);
        try {
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分享单个文件
     */
    public static void shareFile(Activity activity,File file){
        Uri uri = CrashFileTool.getUri(activity, file);
        shareFile(activity,uri,"text/plain");
    }

}
