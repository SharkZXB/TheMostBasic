package com.sharkz.crashlib;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/8/8  13:47
 * 描    述
 * 修订历史：
 * ================================================
 */
public class CrashManager {

    private static Application application;

    public static Application getApplication() {
        return application;
    }


    // =============================================================================================


    /**
     * 这里记得修改成自己的 App crash log file name
     */
    public static final String CRASH_LOG_LOCAL_FILE_NAME = "shark_crash_log";

    /**
     * 初始化
     */
    public static void init(Application application) {
        if (!BuildConfig.DEBUG) {
            return;
        }
        init(application, CRASH_LOG_LOCAL_FILE_NAME);
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

        CrashManager.application = application;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.CHINA);
        String fileName = format.format(new Date(System.currentTimeMillis())) + ".txt";

        CrashLogConfig.getInstance()
                .isWriteLog(true)//是否在文件中记录，默认不记录
                .isSaveExternal(true) // 默认保存在应用内
                .setFileName(fileName)
                .setSaveFileTime(24 * 60 * 60 * 1000)//设置清空日志的时间 默认是一天清理一次日志
                .setCrashName("crash_" + fileName)
                .setFolderName(shark_crash_log)  // 文件夹的名字
                .fileSize(100 * 1024)//日志文件的大小，默认0.1M,以bytes为单位
                .setJump2ShowExceptionActivity(true) // 界面跳转
                .build(application)
                .start();
    }


    // =============================================================================================


    /**
     * 调用该方法可以将奔溃信息文件分享到第三方，如微信等
     *
     * @param activity 调用分享的activity
     */
    public static void shareCrashFile(Activity activity) {
        if (application == null) {
            throw new IllegalStateException("CrashManager尚未被初始化，请先调用init完成初始化！！！！");
        }
        CrashShareTool.shareCrashFile(activity);
    }

    /**
     * 跳转到显示 日志列表的界面
     *
     * @param activity activity
     */
    public static void jump2CrashLogListActivity(Activity activity) {
        Intent intent = new Intent(activity, CrashLogListActivity.class);
        activity.startActivity(intent);
    }

}
