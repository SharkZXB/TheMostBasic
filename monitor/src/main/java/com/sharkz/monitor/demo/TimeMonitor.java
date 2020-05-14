package com.sharkz.monitor.demo;

import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/4/20  09:56
 * 描    述 统计耗时的数据结构
 * 修订历史：
 * ================================================
 */
@Deprecated
public class TimeMonitor {

    private final String TAG = "TimeMonitor";                       //
    private int monitorId = -1;                                     //
    private HashMap<String, Long> mTimeTag = new HashMap<>();       //
    private long mStartTime = 0;                                    //


    // =============================================================================================


    public TimeMonitor(int monitorId) {
        this.monitorId = monitorId;
    }

    public int getMonitorId() {
        return monitorId;
    }

    /**
     * 开始
     */
    public void startMonitor() {
        if (mTimeTag.size() > 0) {
            mTimeTag.clear();
        }
        mStartTime = System.currentTimeMillis();
    }

    /**
     * 打一次点，tag交线需要统计的上层定义
     *
     * @param tag
     */
    public void recodingTimeTag(String tag) {
        // 检查是否保存过相同的tag
        if (mTimeTag.get(tag) != null) {
            mTimeTag.remove(tag);
        }
        long time = System.currentTimeMillis() - mStartTime;
//        Log.e(TAG, tag + "：" + time);
        mTimeTag.put(tag, time);
    }

    /**
     * 结束
     *
     * @param tag
     * @param writeLog
     */
    public void end(String tag, boolean writeLog) {
        recodingTimeTag(tag);
        end(writeLog);
    }

    public void end(boolean writeLog) {
        if (writeLog) {
            // 写入到本地文件
        }
        showDataToLogcat();
    }

    private void showDataToLogcat() {
        if (mTimeTag.size() <= 0) {
            Log.e(TAG, "mTimeTag is empty");
        }
        Iterator iterator = mTimeTag.keySet().iterator();
        while (iterator.hasNext()) {
            String tag = (String) iterator.next();
            Log.e(TAG, tag + "：" + mTimeTag.get(tag));
        }
    }

    public HashMap<String, Long> getmTimeTag() {
        return mTimeTag;
    }

}
