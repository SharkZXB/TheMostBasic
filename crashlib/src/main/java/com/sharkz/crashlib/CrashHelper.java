package com.sharkz.crashlib;

import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/8/8  13:48
 * 描    述
 * 修订历史：
 * ================================================
 */
public class CrashHelper {

    private static String defaultDir;   // 默认的日志保存路径
    private static String versionName;  // 当前App 版本名
    private static int versionCode;     // 当前App 版本号

    private static final String FILE_SEP = System.getProperty("file.separator");
    @SuppressLint("SimpleDateFormat")
    private static final Format FORMAT =
            new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");

    // =============================================================================================


    private CrashHelper() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    static {
        try {
            PackageInfo pi = CrashManager.getApplication()
                    .getPackageManager()
                    .getPackageInfo(CrashManager.getApplication().getPackageName(), 0);
            if (pi != null) {
                versionName = pi.versionName;
                versionCode = pi.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    // =============================================================================================

    /**
     * 拼接奔溃信息
     */
    public static String buildCrashInfo(Throwable e) {
        final StringBuilder sb = new StringBuilder();
        final String time = FORMAT.format(new Date(System.currentTimeMillis()));
        final String head = "************* Log Head ****************"
                + "\nTime Of Crash      : "
                + time
                + "\nDevice Manufacturer: "
                + Build.MANUFACTURER
                + "\nDevice Model       : "
                + Build.MODEL
                + "\nAndroid Version    : "
                + Build.VERSION.RELEASE
                + "\nAndroid SDK        : "
                + Build.VERSION.SDK_INT
                + "\nApp VersionName    : "
                + versionName
                + "\nApp VersionCode    : "
                + versionCode
                + "\n************* Log Head ****************\n\n";
        sb.append(head);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        Throwable cause = e.getCause();
        while (cause != null) {
            cause.printStackTrace(pw);
            cause = cause.getCause();
        }
        pw.flush();
        sb.append(sw.toString());
        return sb.toString();
    }

    /**
     * 获取异常信息
     */
    public static String collectCrashInfo(Throwable ex) {
        if (ex == null) return "";
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable throwable = ex.getCause();
        while (throwable != null) {
            throwable.printStackTrace(printWriter);
            throwable = throwable.getCause();//逐级获取错误信息
        }
        String crashInfo = writer.toString();
        printWriter.close();
        return crashInfo;
    }

    /**
     * 获取手机信息
     */
    public static String collectDeviceInfo() {
        StringBuilder sb = new StringBuilder();

        //App版本
        sb.append("App Version: ");
        sb.append(BuildConfig.VERSION_NAME);
        sb.append("_");
        sb.append(BuildConfig.VERSION_CODE + "\n");

        //Android版本号
        sb.append("OS Version: ");
        sb.append(Build.VERSION.RELEASE);
        sb.append("_");
        sb.append(Build.VERSION.SDK_INT + "\n");

        //手机制造商
        sb.append("Vendor: ");
        sb.append(Build.MANUFACTURER + "\n");

        //手机型号
        sb.append("Model: ");
        sb.append(Build.MODEL + "\n");

        //CPU架构
        sb.append("CPU: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sb.append(Arrays.toString(Build.SUPPORTED_ABIS));
        } else {
            sb.append(Build.CPU_ABI);
        }

        //屏幕刷新频率
        //
        return sb.toString();
    }


    // =============================================================================================

    /**
     * 将奔溃信息存储到本地
     */
    public static void saveCrashLogToLocal(Thread t, Throwable e){
        // 获取设备的详情
        String deviceInfo = collectDeviceInfo();
        // 获取crash详情
        String crashInfo = collectCrashInfo(e);

        // 保存信息到文件
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss", Locale.CHINA);
        String currentTime = formatter.format(new Date());
        CrashFileTool.writeLogFile(currentTime + "\n" + deviceInfo + "\n" + crashInfo, CrashLogConfig.getInstance().getCrashName());
    }

    /**
     * get default crash log dir
     *
     * @return dir
     */
//    public  static String getDefaultCrashDir() {
//        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
//                && CrashManager.getApplication().getExternalCacheDir() != null) {
//            return CrashManager.getApplication().getExternalCacheDir()
//                    + FILE_SEP
//                    + "crash_log"
//                    + FILE_SEP;
//        } else {
//            return CrashManager.getApplication().getCacheDir()
//                    + FILE_SEP
//                    + "crash_log"
//                    + FILE_SEP;
//        }
//    }

}
