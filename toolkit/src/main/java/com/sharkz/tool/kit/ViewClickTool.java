package com.sharkz.tool.kit;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/6/15  12:16
 * 描    述 View 多次点击事件 , View 点击锁定背景颜色修改 等
 * 修订历史：
 * ================================================
 */
public class ViewClickTool {

    private static long lastClickTime = 0;
    private static long DIFF = 1000;
    private static int lastButtonId = -1;

    /**
     * 判断两次点击的间隔，如果小于1000，则认为是多次无效点击
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(-1, DIFF);
    }

    /**
     * 判断两次点击的间隔，如果小于1000，则认为是多次无效点击
     *
     * @return
     */
    public static boolean isFastDoubleClick(int buttonId) {
        return isFastDoubleClick(buttonId, DIFF);
    }

    /**
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击
     *
     * @param diff
     * @return
     */
    public static boolean isFastDoubleClick(int buttonId, long diff) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < diff) {
            // Log.v("isFastDoubleClick", "短时间内按钮多次触发");
            return true;
        }
        lastClickTime = time;
        lastButtonId = buttonId;
        return false;
    }


    // =============================================================================================


    /**
     * 状态回调
     */
    public interface ViewStateChangeListener {

        void lock(View view);    // View 锁定

        void unLock(View view);  // View 解锁
    }

    private static List<ViewStateChangeListener> changeList;
    private static List<View> viewList;

    /**
     * 添加回调监听
     */
    public static void addViewStateChangeListener(ViewStateChangeListener listener) {
        if (changeList == null) {
            changeList = new ArrayList<>();
        }
        changeList.add(listener);
    }

    /**
     * 移除监听回调
     */
    public static void removeViewStateChangeListener(ViewStateChangeListener listener) {
        if (changeList == null) {
            return;
        }
        changeList.remove(listener);
    }

    /**
     * 锁定
     */
    public static void lockView(View view) {
        if (changeList == null) {
            changeList = new ArrayList<>();
        }
        viewList.add(view);
        if (changeList != null) {
            for (ViewStateChangeListener l : changeList) {
                l.lock(view);
            }
        }
    }

    /**
     * 解除锁定
     */
    public static void unLock(View view) {
        if (changeList == null) {
            return;
        }
        viewList.remove(view);
        if (changeList != null) {
            for (ViewStateChangeListener l : changeList) {
                l.unLock(view);
            }
        }
    }

}
