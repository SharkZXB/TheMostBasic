package com.sharkz.crashlib;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/8/8  13:49
 * 描    述
 * 修订历史：
 * ================================================
 */
public class CrashFileTool {

    // private final static String TAG = "CrashFileHelper";

    /**
     * 这个和 manifest 里面的要一样
     */
    public static final String FILE_PROVIDER_NAME = "fileprovider";

    /**
     * 获取文件对应的URI
     *
     * @param context
     * @param file
     * @return
     */
    public static Uri getUri(Context context, File file) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //data是file类型,忘了复制过来
            uri = FileProvider.getUriForFile(context, context.getPackageName() + "." + FILE_PROVIDER_NAME, file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    /**
     * 压缩文件
     *
     * @param fs          需要压缩的文件
     * @param zipFilePath 被压缩后存放的路径
     * @return 成功返回 true，否则 false
     */
    public static boolean zipFiles(File fs[], String zipFilePath) {
        if (fs == null) {
            throw new NullPointerException("fs == null");
        }
        boolean result = false;
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFilePath)));
            for (File file : fs) {
                if (file == null || !file.exists() || file.getName().toLowerCase().endsWith("zip")) {
                    continue;
                }
                if (file.isDirectory()) {
                    recursionZip(zos, file, file.getName() + File.separator);
                } else {
                    recursionZip(zos, file, "");
                }
            }
            result = true;
            zos.flush();
        } catch (Exception e) {
            e.printStackTrace();
            // Log.e(TAG, "zip file failed err: " + e.getMessage());
        } finally {
            try {
                if (zos != null) {
                    zos.closeEntry();
                    zos.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 递归压缩
     *
     * @param zipOut
     * @param file
     * @param baseDir
     * @throws Exception
     */
    public static void recursionZip(ZipOutputStream zipOut, File file, String baseDir) throws Exception {
        if (file.isDirectory()) {
            // Log.i(TAG, "the file is dir name -->>" + file.getName() + " the baseDir-->>>" + baseDir);
            File[] files = file.listFiles();
            for (File fileSec : files) {
                if (fileSec == null) {
                    continue;
                }
                if (fileSec.isDirectory()) {
                    baseDir = file.getName() + File.separator + fileSec.getName() + File.separator;
                    // Log.i(TAG, "basDir111-->>" + baseDir);
                    recursionZip(zipOut, fileSec, baseDir);
                } else {
                    // Log.i(TAG, "basDir222-->>" + baseDir);
                    recursionZip(zipOut, fileSec, baseDir);
                }
            }
        } else {
            // Log.i(TAG, "the file name is -->>" + file.getName() + " the base dir -->>" + baseDir);
            byte[] buf = new byte[2048];
            InputStream input = new BufferedInputStream(new FileInputStream(file));
            zipOut.putNextEntry(new ZipEntry(baseDir + file.getName()));
            int len;
            while ((len = input.read(buf)) != -1) {
                zipOut.write(buf, 0, len);
            }
            input.close();
        }
    }

    /**
     * 将log日志写入本地文件
     *
     * @param msg      日志信息
     * @param fileName 文件名
     */
    public static void writeLogFile(String msg, String fileName) {
        synchronized (CrashFileTool.class) {
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
     * 读取日志
     */
    public static String readLogText() {
        return readLogText(new File(CrashLogConfig.getInstance().getLogFilePath(), CrashLogConfig.getInstance().getFileName()));
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
     * 获取所有的日志文件
     */
    public static List<File> getLogFileListV2() {
        List<File> list = new ArrayList<>();
        File file = new File(CrashLogConfig.getInstance().getLogFilePath());
        if (!file.isFile()) {
            File[] listFiles = file.listFiles();
            if (listFiles!=null) {
                return Arrays.asList(listFiles);
            }
        }
        return list;
    }


    /**
     * 删除过期的日志文件
     */
    public static void deleteOverdueFile() {
        File[] files = CrashFileTool.getLogFileList();
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

    /**
     * 删除指定文件
     *
     * @param file 目标文件
     */
    public static void deleteFile(@NonNull File file) {
        if (file.exists()) {
            file.delete();
        }
    }

}
