package com.sharkz.tool.kit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2019/12/24
 * 描    述：屏幕相关工具类
 * 修订历史：
 * ================================================
 */
public class ScreenTool {


    /**
     * 获取屏幕区域的宽高等尺寸获取 --> 整个手机界面的宽高 --> 状态栏高度+ActionBar高度+View布局高度
     */
    public static int screenWidth(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int screenHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }


    /**
     * 应用程序App区域宽高等尺寸获取 --> ActionBar+ContentView
     * 这些方法最好在Activity的onWindowFocusChanged ()方法或者之后调运，因为只有这时候才是真正的显示OK
     */
    public static int appWidth(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.width();
    }

    public static int appHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int h = rect.height();
        return rect.height();
    }

    /**
     * 获取ActionBar的高度
     */
    public static int actionBarHeight(Context context) {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }


    /**
     * View布局区域宽高等尺寸获取
     * 这些方法最好在Activity的onWindowFocusChanged ()方法或者之后调运，因为只有这时候才是真正的显示OK
     */
    public static int contentViewWidth(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(rect);
        return rect.width();
    }

    public static int contentViewHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(rect);
        return rect.height();
    }

    /**
     * 获取当前 View(左上角) 在屏幕中的位置(X,Y)
     */
    public static int viewGetLocationOnScreenX(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location[0];
    }

    public static int viewGetLocationOnScreenY(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location[1];
    }

    /**
     * 获取当前 View(左上角) 在Window中的位置(X,Y)
     */
    public static int viewGetLocationInWindowX(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return location[0];
    }

    public static int viewGetLocationInWindowY(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return location[1];
    }


    /**
     * 获取底栏高度
     *
     * @return
     */
    public int getNavbarHeight(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            WindowInsets windowInsets = null;
            windowInsets = activity.getWindow().getDecorView().getRootView().getRootWindowInsets();
            if (windowInsets != null) {
                return windowInsets.getStableInsetBottom();
            }
        }
        int resourceId = 0;
        int rid = activity.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            resourceId = activity.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return activity.getResources().getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    /**
     * 获取状态栏高度
     */
    public static int statusBarHeight(Activity activity) {
        // Rect rect = new Rect();
        // activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        // return rect.top;

        int statusBarHeight = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

}
