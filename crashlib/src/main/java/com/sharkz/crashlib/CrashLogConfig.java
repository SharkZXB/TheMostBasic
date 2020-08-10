package com.sharkz.crashlib;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/6/16  15:50
 * 描    述 这个类是配置设置
 * 修订历史：
 * ================================================
 */
public class CrashLogConfig {


    /**
     * 这里记得修改成自己的 App crash log file name 默认的文件夹名字
     */
    public static final String CRASH_LOG_LOCAL_FILE_NAME = "shark_crash_log";

    /**
     * 日志是否保存在SDCard 默认保存在App下
     */
    private boolean writeLog = false;

    /**
     * 是否保存在外部存储
     */
    private boolean isSaveExternal = false;

    /**
     * log日志的文件名称
     */
    private String fileName = "log.txt";

    /**
     * log日志的文件名称
     */
    private String crashName = "crash.txt";

    /**
     * 文件夹名字
     */
    private String folderName = "log";

    /**
     * 设置清空日志的时间 默认是一天清理一次日志
     */
    private long saveFileTime = 1 * 24 * 60 * 60 * 1000;

    /**
     * 日志文件的大小，默认0.1M
     */
    private long fileSize = 100000;

    private String logFilePath;       // crash log 存储的位置
    private boolean isJump2ShowExceptionActivity = false; // 捕获异常之后 跳转到界面展示


    // =============================================================================================


    private CrashLogConfig() {
    }

    private static class INSTANCE {
        private static CrashLogConfig logConfig = new CrashLogConfig();
    }

    public static CrashLogConfig getInstance() {
        return INSTANCE.logConfig;
    }


    // =============================================================================================


    /**
     * 日志是否写入本地文件
     */
    public CrashLogConfig isWriteLog(boolean isWriteLog) {
        writeLog = isWriteLog;
        return this;
    }

    public boolean getIsWriteLog() {
        return writeLog;
    }

    /**
     * 文件缓存大小
     */
    public CrashLogConfig fileSize(long size) {
        this.fileSize = size;
        return this;
    }

    public long getFileSize() {
        return this.fileSize;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public String getFileName() {
        return fileName;
    }

    public CrashLogConfig setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getFolderName() {
        return folderName;
    }

    public CrashLogConfig setFolderName(String folderName) {
        this.folderName = folderName;
        return this;
    }

    public boolean getSaveExternal() {
        return isSaveExternal;
    }

    public CrashLogConfig isSaveExternal(boolean saveExternal) {
        isSaveExternal = saveExternal;
        return this;
    }

    public String getCrashName() {
        return crashName;
    }

    public CrashLogConfig setCrashName(String crashName) {
        this.crashName = crashName;
        return this;
    }

    public long getSaveFileTime() {
        return saveFileTime;
    }

    public CrashLogConfig setSaveFileTime(long saveFileTime) {
        this.saveFileTime = saveFileTime;
        return this;
    }

    public CrashLogConfig setJump2ShowExceptionActivity(boolean isJump2ShowExceptionActivity) {
        this.isJump2ShowExceptionActivity = isJump2ShowExceptionActivity;
        return this;
    }

    public boolean isJump2ShowExceptionActivity() {
        return isJump2ShowExceptionActivity;
    }


    // =============================================================================================


    /**
     * Build
     *
     * @param context 上下文
     * @return
     */
    public CrashLogConfig build(Context context) {
        if (isSaveExternal) {
            // 优先保存到SD卡中
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                logFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + folderName + File.separator;
            } else {
                logFilePath = context.getFilesDir().getAbsolutePath() + File.separator + folderName + File.separator;
            }
        } else {
            // 保存在 内置存储中
            logFilePath = context.getFilesDir().getAbsolutePath() + File.separator + folderName + File.separator;
        }

        File file = new File(logFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        // 清理一遍 历史文件
        CrashFileTool.deleteOverdueFile();
        return this;
    }

    /**
     * 开启 crash log 本地保存
     */
    public void start() {
        if (writeLog) {
            new ExceptionCaughtHandler();
            // new LogHelper().start();
        }
    }
}
