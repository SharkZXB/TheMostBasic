package com.sharkz.tool;

import android.app.Application;

//import com.sharkz.tool.kit.DoraemonKitTool;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020-01-14  16:08
 * 描    述 工具类统一初始化
 * 修订历史：
 * ================================================
 */
public class Init_Tool {

    /**
     * 当前Module 初始化传入 application
     */
    public static Application application;

    /**
     * 工具类里面的初始化统一在这里完成
     *
     * @param application
     */
    public static void init(Application application) {

        if (application==null){
            throw new NullPointerException("Init_Tool --> init(Application application ) the application is null");
        }

        Init_Tool.application = application;

        /**
         * SP
         */
      //  PreferenceUtils.initSP(application);

        /**
         * 哆啦A梦
         */
       // DoraemonKitTool.init(application);

        /**
         * 内存泄漏
         */
       // LeakCanaryTool.setupLeakCanary(application);
    }

}

