package com.sharkz.crashlib;

import android.os.SystemClock;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/8/8  13:50
 * 描    述 在这里接受全局没有捕获的异常
 * 修订历史：
 * ================================================
 */
public class ExceptionCaughtHandler implements Thread.UncaughtExceptionHandler {

    public ExceptionCaughtHandler() {
        //设置该CrashHandler为系统默认的
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        // 获取异常详情
        String crashInfo = CrashHelper.buildCrashInfo(ex);

        // 保存日志到本地信息
        CrashHelper.saveCrashLogToLocal(thread, ex);

        // debug模式下才显性的展示崩溃信息
        if (CrashLogConfig.getInstance().isJump2ShowExceptionActivity()) {
            ShowExceptionActivity.showException(crashInfo);
        }

        //延迟2秒杀进程 用于数据保存
        SystemClock.sleep(2000);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}