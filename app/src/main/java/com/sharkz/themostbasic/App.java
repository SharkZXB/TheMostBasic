package com.sharkz.themostbasic;

import android.app.Application;

import com.sharkz.crashlib.CrashManager;
import com.sharkz.monitor.LoggerTool;
import com.sharkz.monitor.crashlog.SharkCrashLog;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/8/8  11:49
 * 描    述
 * 修订历史：
 * ================================================
 */
public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        SharkCrashLog.init(this);
        LoggerTool.getInstance().init();

        // 注册
        CrashManager.init(this);
    }
}
