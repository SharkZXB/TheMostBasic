package com.sharkz.monitor.crashlog;

import android.os.Build;
import android.os.SystemClock;

import com.sharkz.monitor.BuildConfig;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/6/16  15:47
 * 描    述 TODO 在这里统一处理异常
 * 关于UncaughtExceptionHandler，它是Java Thread类中定义的一个接口；
 * 用于处理未捕获的异常导致线程的终止（注意：catch了的是捕获不到的），
 * 当我们的应用crash的时候，就会走UncaughtExceptionHandler的uncaughtException方法，
 * 在该方法中可以获取到异常的信息，我们通过setDefaultUncaughtExceptionHandler该方法来设置线程的默认异常处理器，
 * 将异常信息保存到本地或者是上传到服务器，方便我们快速的定位问题；
 * 修订历史：
 * ================================================
 */
public class CrashHelper implements Thread.UncaughtExceptionHandler {


    public CrashHelper() {
        //设置该CrashHandler为系统默认的
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

        // 获取设备的详情
        String deviceInfo = collectDeviceInfo();
        // 获取crash详情
        String crashInfo = collectCrashInfo(e);

        // 保存信息到文件
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss", Locale.CHINA);
        String currentTime = formatter.format(new Date());
        CrashLogFileUtils.writeLogFile(currentTime + "\n" + deviceInfo + "\n" + crashInfo, CrashLogConfig.getInstance().getCrashName());

        //延迟2秒杀进程 用于数据保存
        SystemClock.sleep(2000);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


    // =============================================================================================


    /**
     * 获取异常信息
     */
    private String collectCrashInfo(Throwable ex) {
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
    private String collectDeviceInfo() {
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
}
