package com.sharkz.datastorage.sp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


/**
 * ================================================
 * 作    者：SharkZ
 * 版    本：1.0
 * 创建日期：2018/1/2
 * 描    述：当前 是用来 管理 偏好设置的
 * 修订历史：
 * ================================================
 */
public class PreferenceUtils {

    /**
     * 偏好设置 Name
     */
    public static final String DEFAULT_PREFERENCE_NAME = "shark_preference";

    /**
     * 获取上下文 这里 用 application 接受 便于 后期替换 BaseApplication.getApplication() 表达式
     */
    private static Application application = null;

    /**
     * 初始化
     */
    public static void initSP(Application application) {
        PreferenceUtils.application = application;
    }

    /**
     * 存入 String
     *
     * @param key   key
     * @param value value
     * @return
     */
    public static boolean putString(String key, String value) {
        checkContext();
        //获取shareprefrence对象
        SharedPreferences settings = application.getSharedPreferences(DEFAULT_PREFERENCE_NAME, Context.MODE_PRIVATE);
        //获取shareprefrence的编辑器对象
        SharedPreferences.Editor editor = settings.edit();
        //通过编辑器对象往里面放入value
        editor.putString(key, value);
        return editor.commit();
    }


    /**
     * 获取String
     *
     * @param key key
     * @return
     */
    public static String getString(String key) {
        checkContext();
        return getString(key, null);
    }


    /**
     * 获取String 待默认值的
     *
     * @param key          key
     * @param defaultValue 默认值
     * @return
     */
    public static String getString(String key, String defaultValue) {
        checkContext();
        SharedPreferences settings = application.getSharedPreferences(DEFAULT_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }


    /**
     * 保存 Int
     *
     * @param key   key
     * @param value value
     * @return
     */
    public static boolean putInt(String key, int value) {
        checkContext();
        SharedPreferences settings = application.getSharedPreferences(DEFAULT_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * 获取 Int  默认 -1
     *
     * @param key key
     * @return
     */
    public static int getInt(String key) {
        checkContext();
        return getInt(key, -1);
    }

    /**
     * 获取 Int  指定默认值
     *
     * @param key          key
     * @param defaultValue 指定默认值
     * @return
     */
    public static int getInt(String key, int defaultValue) {
        checkContext();
        SharedPreferences settings = application.getSharedPreferences(DEFAULT_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }


    /**
     * 保存 Long
     *
     * @param key   key
     * @param value value
     * @return
     */
    public static boolean putLong(String key, long value) {
        checkContext();
        SharedPreferences settings = application.getSharedPreferences(DEFAULT_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * get long preferences 默认 -1
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is CourseIntroduceBean preference with this
     * name that is not CourseIntroduceBean long
     * @see #getLong(String, long)
     */
    public static long getLong(String key) {
        checkContext();
        return getLong(key, -1);
    }

    /**
     * get long preferences 指定默认值
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is CourseIntroduceBean preference with
     * this name that is not CourseIntroduceBean long
     */
    public static long getLong(String key, long defaultValue) {
        checkContext();
        SharedPreferences settings = application.getSharedPreferences(DEFAULT_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }


    /**
     * put float preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putFloat(String key, float value) {
        checkContext();
        SharedPreferences settings = application.getSharedPreferences(DEFAULT_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * get float preferences
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is CourseIntroduceBean preference with this
     * name that is not CourseIntroduceBean float
     * @see #getFloat(String, float)
     */
    public static float getFloat(String key) {
        checkContext();
        return getFloat(key, -1);
    }

    /**
     * get float preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is CourseIntroduceBean preference with
     * this name that is not CourseIntroduceBean float
     */
    public static float getFloat(String key, float defaultValue) {
        checkContext();
        SharedPreferences settings = application.getSharedPreferences(DEFAULT_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }


    /**
     * put boolean preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putBoolean(String key, boolean value) {
        checkContext();
        SharedPreferences settings = application.getSharedPreferences(DEFAULT_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * get boolean preferences, default is false
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or false. Throws ClassCastException if there is CourseIntroduceBean preference with this
     * name that is not CourseIntroduceBean boolean
     * @see #getBoolean(String, boolean)
     */
    public static boolean getBoolean(String key) {
        checkContext();
        return getBoolean(key, false);
    }

    public static boolean getDefaultBoolean(String key) {
        checkContext();
        return getBoolean(key, true);
    }

    /**
     * get boolean preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is CourseIntroduceBean preference with
     * this name that is not CourseIntroduceBean boolean
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        checkContext();
        SharedPreferences settings = application.getSharedPreferences(DEFAULT_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }


    /**
     * 清空全部的数据
     */
    public static void cleanAllData() {
        checkContext();
        SharedPreferences settings = application.getSharedPreferences(DEFAULT_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 检查是否初始化
     */
    private static void checkContext() {
        if (application == null)
            throw new NullPointerException(" --- PreferenceUtils 没有初始化你就用 想什么呢 --- ");
    }


}
