package com.sharkz.tool.kit;


import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * ================================================
 * 作    者：SharkZ
 * 版    本：1.0
 * 创建日期：2019/11/13
 * 描    述：LeakCanary Tool
 * 修订历史：
 * ================================================
 */
public class LeakCanaryTool {

    private static RefWatcher refWatcher;

    public static void setupLeakCanary(Application application) {
        if (LeakCanary.isInAnalyzerProcess(application)) {
            refWatcher = RefWatcher.DISABLED;
            return;
        }
        refWatcher = LeakCanary.install(application);
    }

    public static RefWatcher getLeakCanary() {
        if (refWatcher == null) {
            throw new NullPointerException("LeakCanaryTool 没有初始化");
        }
        return refWatcher;
    }

}
