package com.sharkz.monitor;

import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/4/20  10:23
 * 描    述
 * 修订历史：
 * ================================================
 */
public class TimeMonitorManager {

    private static TimeMonitorManager mTimeMonitorManager = null;       // 当前
    private HashMap<String, Long> timeMonitorList;                     // 用来保存 tag 和 开始时间的


    // =============================================================================================


    /**
     * 获取单列
     */
    public synchronized static TimeMonitorManager getInstance() {
        if (mTimeMonitorManager == null) {
            mTimeMonitorManager = new TimeMonitorManager();
        }
        return mTimeMonitorManager;
    }

    private TimeMonitorManager() {
        timeMonitorList = new HashMap<>();
    }


    // =============================================================================================


    /**
     * 空检查
     *
     * @param tag 唯一标示
     */
    private void checkNull(String tag) {
        if (TextUtils.isEmpty(tag)) {
            throw new NullPointerException("传入的 tag 不可以为空 ");
        }
    }

    /**
     * 打点开始
     */
    public void startMonitor(String tag) {
        checkNull(tag);
        timeMonitorList.put(tag, System.currentTimeMillis());
    }

    /**
     * 打点结束
     */
    public void endMonitor(String tag) {
        checkNull(tag);
        // 打印 tag 以及消耗的时间
        Log.e(tag, String.valueOf(System.currentTimeMillis() - timeMonitorList.get(tag)));
    }

}
