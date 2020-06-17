package com.sharkz.monitor.crashlog;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/6/16  15:51
 * 描    述 文件操作类
 * 修订历史：
 * ================================================
 */
public class CrashLogFileUtils {

    private static Object obj = new Object();

    /**
     * 将log日志写入本地文件
     *
     * @param msg      日志信息
     * @param fileName 文件名
     */
    public static void writeLogFile(String msg, String fileName) {
        synchronized (obj) {
            try {
                File file = new File(CrashLogConfig.getInstance().getLogFilePath(), fileName);
                FileWriter fw = null;
                if (file.exists()) {
                    if (file.length() > CrashLogConfig.getInstance().getFileSize())
                        fw = new FileWriter(file, false);
                    else
                        fw = new FileWriter(file, true);
                } else
                    fw = new FileWriter(file, false);

                fw.write(String.format("%s", msg));
                fw.flush();
                fw.close();
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 读取日志信息
     *
     * @param file 文件
     */
    public static String readLogText(File file) {
        FileReader fr = null;
        try {
            if (!file.exists()) {
                return "";
            }
            long n = CrashLogConfig.getInstance().getFileSize();
            long len = file.length();
            long skip = len - n;
            fr = new FileReader(file);
            fr.skip(Math.max(0, skip));
            char[] cs = new char[(int) Math.min(len, n)];
            fr.read(cs);
            return new String(cs).trim();
        } catch (Throwable ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fr != null)
                    fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 读取日志
     */
    public static String readLogText() {
        return readLogText(new File(CrashLogConfig.getInstance().getLogFilePath(), CrashLogConfig.getInstance().getFileName()));
    }

    /**
     * 获取所有的日志文件
     */
    public static File[] getLogFileList() {
        File file = new File(CrashLogConfig.getInstance().getLogFilePath());
        if (!file.isFile()) {
            return file.listFiles();
        }
        return null;
    }

    /**
     * 删除过期的日志文件
     */
    public static void deleteOverdueFile() {
        File[] files = CrashLogFileUtils.getLogFileList();
        if (files == null) return;
        long current = System.currentTimeMillis();
        for (File file : files) {
            if (file.exists()) {
                long time = file.lastModified(); // 获取文件的最后修改时间
                if (current - time > CrashLogConfig.getInstance().getSaveFileTime()) {
                    file.delete();
                }
            }
        }
    }
}
