package com.sharkz.tool.kit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import androidx.annotation.NonNull;

import static android.content.Context.POWER_SERVICE;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2019/12/26
 * 描    述：电池优化
 * 修订历史：https://mp.weixin.qq.com/s/DYxVucxWwTt0D61flsVv5w
 * ================================================
 */
public class BatteryOptimization {

    /**
     * 请求忽略电池优化 如果已经优化直接返回 没有跳转到设置界面
     *
     * @param activity activity
     */
    public static void ignoreBatteryOptimization(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 这个类使您能够控制设备的电源状态。
            PowerManager powerManager = (PowerManager) activity.getSystemService(POWER_SERVICE);
            if (powerManager == null) return;
            // 将当前App 忽略电池优化
            boolean hasIgnored = powerManager.isIgnoringBatteryOptimizations(activity.getPackageName());
            //  判断当前APP是否有加入电池优化的白名单，如果没有，弹出加入电池优化的白名单的设置对话框。
            if (!hasIgnored) {
                @SuppressLint("BatteryLife") Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + activity.getPackageName()));
                activity.startActivity(intent);
            }
        }
    }


    // =============================================================================================


    /**
     * 跳转到指定应用的首页
     */
    private void showActivity(@NonNull String packageName, @NonNull Context context) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
    }

    /**
     * 跳转到指定应用的指定页面
     */
    private void showActivity(@NonNull String packageName, @NonNull String activityDir, @NonNull Context context) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, activityDir));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }



    // =============================================================================================


    /**
     * 华为手机
     */
    public boolean isHuawei() {
        if (Build.BRAND == null) {
            return false;
        } else {
            return Build.BRAND.toLowerCase().equals("huawei") || Build.BRAND.toLowerCase().equals("honor");
        }
    }

    public void goHuaweiSetting(Context context) {
        try {
            showActivity("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity", context);
        } catch (Exception e) {
            showActivity("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.bootstart.BootStartActivity", context);
        }
    }


    // =============================================================================================

    /**
     * 小米
     */
    public static boolean isXiaomi() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("xiaomi");
    }

    public void goXiaomiSetting(Context context) {
        showActivity("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity", context);
    }


    // =============================================================================================

    /**
     * OPPO
     */
    public static boolean isOPPO() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("oppo");
    }

    public void goOPPOSetting(Context context) {
        try {
            showActivity("com.coloros.phonemanager", context);
        } catch (Exception e1) {
            try {
                showActivity("com.oppo.safe", context);
            } catch (Exception e2) {
                try {
                    showActivity("com.coloros.oppoguardelf", context);
                } catch (Exception e3) {
                    showActivity("com.coloros.safecenter", context);
                }
            }
        }
    }


    // =============================================================================================

    /**
     * VIVO
     */
    public static boolean isVIVO() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("vivo");
    }

    public void goVIVOSetting(Context context) {
        showActivity("com.iqoo.secure", context);
    }


    // =============================================================================================

    /**
     * 魅族
     */
    public static boolean isMeizu() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("meizu");
    }

    public void goMeizuSetting(Context context) {
        showActivity("com.meizu.safe", context);
    }


    // =============================================================================================

    /**
     * 三星
     */
    public static boolean isSamsung() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("samsung");
    }

    public void goSamsungSetting(Context context) {
        try {
            showActivity("com.samsung.android.sm_cn", context);
        } catch (Exception e) {
            showActivity("com.samsung.android.sm", context);
        }
    }


    // =============================================================================================


    /**
     * 乐视
     */
    public static boolean isLeTV() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("letv");
    }

    public void goLetvSetting(Context context) {
        showActivity("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity", context);
    }


    // =============================================================================================


    /**
     * 锤子
     */
    public static boolean isSmartisan() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("smartisan");
    }

    public void goSmartisanSetting(Context context) {
        showActivity("com.smartisanos.security", context);
    }

}
