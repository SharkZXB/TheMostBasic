package com.sharkz.monitor.crashlog;

import android.app.Application;

import com.sharkz.monitor.BuildConfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/6/16  15:57
 * 描    述
 * 修订历史：
 * ================================================
 */
public class SharkCrashLog {

    /**
     * 初始化
     */
    public static void init(Application application) {

        if (!BuildConfig.DEBUG) {
            return;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.CHINA);
        String fileName = format.format(new Date(System.currentTimeMillis())) + ".log";

        CrashLogConfig.getInstance()
                .isWriteLog(true)//是否在文件中记录，默认不记录
                .isSaveExternal(true) // 默认保存在应用内
                .setFileName(fileName)
                .setSaveFileTime(24 * 60 * 60 * 1000)//设置清空日志的时间 默认是一天清理一次日志
                .setCrashName("crash_" + fileName)
                .setFolderName("shark_crash_log")  // 文件夹的名字
                .fileSize(100 * 1024)//日志文件的大小，默认0.1M,以bytes为单位
                .build(application)
                .start();
    }


    /**
     * 使用之前必须初始化
     *
     * @param application     上下文
     * @param shark_crash_log 文件夹名字
     */
    public static void init(Application application, String shark_crash_log) {

        if (!BuildConfig.DEBUG) {
            return;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.CHINA);
        String fileName = format.format(new Date(System.currentTimeMillis())) + ".log";

        CrashLogConfig.getInstance()
                .isWriteLog(true)//是否在文件中记录，默认不记录
                .isSaveExternal(true) // 默认保存在应用内
                .setFileName(fileName)
                .setSaveFileTime(24 * 60 * 60 * 1000)//设置清空日志的时间 默认是一天清理一次日志
                .setCrashName("crash_" + fileName)
                .setFolderName(shark_crash_log)  // 文件夹的名字
                .fileSize(100 * 1024)//日志文件的大小，默认0.1M,以bytes为单位
                .build(application)
                .start();
    }

}
