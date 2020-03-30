package com.sharkz.arouter;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020-01-08  13:09
 * 描    述 路由的初始化
 * 修订历史：
 * ================================================
 */
public final class Init_ARouterModule {

    public static void init(Application application, boolean isDebug) {

        // 路由
        if (isDebug) {   // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }

        // 尽可能早，推荐在Application中初始化
        ARouter.init(application);
    }

}
