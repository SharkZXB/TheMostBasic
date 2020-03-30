package com.sharkz.tool.kit;

import android.os.Handler;
import android.os.Looper;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/3/26  13:52
 * 描    述
 * 修订历史：
 * ================================================
 */
public class HandlerUtil {

    /**
     * 注意了这里用到了 UI 线程的 Looper
     */
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void post(Runnable runnable) {
        handler.post(runnable);
    }

    public static void postDelayed(Runnable runnable, long delayMillis) {
        handler.postDelayed(runnable, delayMillis);
    }

    public static void removeAllCallBack() {
        handler.removeCallbacksAndMessages(null);
    }

    public static void removeCallbacks(Runnable runnable) {
        handler.removeCallbacks(runnable);
    }

}
