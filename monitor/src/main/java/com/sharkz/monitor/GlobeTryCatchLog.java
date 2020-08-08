package com.sharkz.monitor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/8/8  11:41
 * 描    述
 * 修订历史：
 * ================================================
 */
public class GlobeTryCatchLog {


    /**
     * 获取异常信息
     * try {
     *                     throw new NullPointerException(" 测试 空指针 ");
     *                 } catch (Exception e) {
     *                     Log.i(TAG, "onClick: collectCrashInfo"+collectCrashInfo(e));
     *                 }
     * I/Shark: onClick: collectCrashInfojava.lang.NullPointerException:  测试 空指针
     *         at com.sharkz.themostbasic.MainActivity$1.onClick(MainActivity.java:51)
     *         at android.view.View.performClick(View.java:6747)
     *         at android.view.View$PerformClick.run(View.java:25461)
     *         at android.os.Handler.handleCallback(Handler.java:790)
     *         at android.os.Handler.dispatchMessage(Handler.java:99)
     *         at android.os.Looper.loop(Looper.java:164)
     *         at android.app.ActivityThread.main(ActivityThread.java:6580)
     *         at java.lang.reflect.Method.invoke(Native Method)
     *         at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:445)
     *         at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:906)
     */
    public static String collectCrashInfo(Exception ex) {
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

}
