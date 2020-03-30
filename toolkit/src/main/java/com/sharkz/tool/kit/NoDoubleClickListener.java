package com.sharkz.tool.kit;

import android.view.View;

import java.util.Calendar;

/**
 * ================================================
 * 作    者：SharkZ
 * 版    本：1.0
 * 创建日期：2019/12/25
 * 描    述：View 多次点击
 * 修订历史：
 * ================================================
 */
public abstract class NoDoubleClickListener implements View.OnClickListener {


    /**
     * 上次点击时间
     */
    private long lastClickTime = 0;

    /**
     * 间隔时间
     */
    private int MIN_CLICK_DELAY_TIME = 1000;

    /**
     * 使用默认的
     */
    public NoDoubleClickListener() {

    }

    /**
     * 自定义传入的时间
     *
     * @param clickTime
     */
    public NoDoubleClickListener(int clickTime) {
        MIN_CLICK_DELAY_TIME = clickTime;
    }

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    public abstract void onNoDoubleClick(View v);

}
