package com.sharkz.monitor.demo;

import android.content.Context;

import java.util.HashMap;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/4/20  10:00
 * 描    述
 * 修订历史：
 * ================================================
 */
@Deprecated
public class TimeMonitorManager2 {

    private static TimeMonitorManager2 mTimeMonitorManager = null;
    private static Context mContext = null;
    private HashMap<Integer, TimeMonitor> timeMonitorList = null;


    /**
     * 获取单列
     */
    public synchronized static TimeMonitorManager2 getInstance() {
        if (mTimeMonitorManager == null) {
            mTimeMonitorManager = new TimeMonitorManager2();
        }
        return mTimeMonitorManager;
    }

    private TimeMonitorManager2() {
        timeMonitorList = new HashMap<>();
    }


    // =============================================================================================


    /**
     * 初始化打点器
     * 当前需要在 Application 的 attachBaseContext 方法中调用
     *
     * @param id
     */
    public void resetTimeMonitor(int id) {
        TimeMonitor monitor = timeMonitorList.get(id);
        if (monitor != null) {
            timeMonitorList.remove(id);
        }
        getTimeMonitor(id);
    }

    /**
     * 获取 TimeMonitor
     *
     * @param id
     * @return
     */
    public TimeMonitor getTimeMonitor(int id) {
        TimeMonitor monitor = timeMonitorList.get(id);
        if (monitor == null) {
            monitor = new TimeMonitor(id);
            monitor.startMonitor();
            timeMonitorList.put(id, monitor);
        }
        return monitor;
    }

}
