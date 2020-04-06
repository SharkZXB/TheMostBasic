package com.sharkz.themostbasic;

import android.app.Application;

import com.sharkz.global.LoggerTool;
import com.sharkz.swipebacklayout.layout.BGASwipeBackHelper;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/4/6  18:00
 * 描    述
 * 修订历史：
 * ================================================
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        BGASwipeBackHelper.init(this,null);
        LoggerTool.getInstance().init();

    }
}
