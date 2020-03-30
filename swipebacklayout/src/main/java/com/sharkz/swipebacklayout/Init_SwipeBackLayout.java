package com.sharkz.swipebacklayout;

import android.app.Application;

import com.sharkz.swipebacklayout.layout.BGASwipeBackHelper;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020-01-08  10:29
 * 描    述 当前Module需要提前初始化
 * 修订历史：
 * ================================================
 */
public final class Init_SwipeBackLayout {

    public static void init(Application application) {
        /**
         * TODO 必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回
         * 第一个参数：应用程序上下文
         * 第二个参数：如果发现滑动返回后立即触摸界面时应用崩溃，请把该界面里比较特殊的 View 的 class 添加到该集合中，目前在库中已经添加了 WebView 和 SurfaceView
         */
        BGASwipeBackHelper.init(application, null);
    }

}
