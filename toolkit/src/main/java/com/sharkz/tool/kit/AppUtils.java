package com.sharkz.tool.kit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

import java.util.List;
import java.util.Locale;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020-01-14  16:08
 * 描    述 App工具类
 * 修订历史：
 * ================================================
 */
public class AppUtils {

    /**
     * 获取应用程序名称
     *
     * @param context 上下文
     * @return 返回当前App的名字
     */
    public static String getAppName(Context context) {
        try {
            PackageInfo packageInfo = getPackInfo(context);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前App版本名称
     *
     * @param context 上下文
     * @return name
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        PackageInfo packInfo = getPackInfo(context);
        if (packInfo != null) {
            versionName = packInfo.versionName;
        }
        return versionName;
    }

    /**
     * 获取当前App版本号
     *
     * @param context 上下文
     * @return 版本号
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        PackageInfo packInfo = getPackInfo(context);
        if (packInfo != null) {
            versionCode = packInfo.versionCode;
        }
        return versionCode;
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private static float density;

    public static int dp2px(float dpValue) {
        if (density == 0) {
            density = Resources.getSystem().getDisplayMetrics().density;
        }
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }


    /**
     * 获取PackageInfo
     */
    private static PackageInfo getPackInfo(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo;
    }


    /**
     * 打开指定App
     *
     * @param packageName 目标App包名
     * @param activity    上下文
     * @return true 目标App存在、成功启动 / false 目标App不存在、打开失败
     */
    public static boolean openApp(String packageName, Activity activity) {
        PackageManager packageManager = activity.getPackageManager();
        if (isInstallApp(packageName, activity)) {
            try {
                Intent intent = packageManager.getLaunchIntentForPackage(packageName);
                activity.startActivity(intent);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 检测App是否已安装
     *
     * @param packageName 目标App包名
     * @param context     上下文
     * @return true 目标App已经安装 / false 目标App没有安装
     */
    public static boolean isInstallApp(String packageName, Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }


    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号   HUAWEI MT7-TL00
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商  Huawei
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     * @return 手机IMEI
     */
    @SuppressLint("HardwareIds")
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return tm.getDeviceId();
            }else{
                throw new NullPointerException(" 当前没有 <uses-permission android:name=\"android.permission.READ_PHONE_STATE\" /> 权限");
            }
        }
        return null;
    }


    /**
     * 当前App 整体处于前台还是后台
     *
     * @param context 上下文
     * @return
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                   // Log.i("后台", appProcess.processName);
                   // Toast.makeText(context, "后台", Toast.LENGTH_SHORT).show();
                    return true;
                }else{
                   // Log.i("前台", appProcess.processName);
                   // Toast.makeText(context, "前台", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        return false;
    }

}
