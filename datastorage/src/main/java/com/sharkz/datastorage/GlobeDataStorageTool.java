package com.sharkz.datastorage;

import android.content.Context;

import com.tencent.mmkv.MMKV;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/4/14  14:58
 * 描    述 全局的数据存储 工具箱
 * 修订历史：
 * ================================================
 */
public class GlobeDataStorageTool {

    // 偏好设置

    // 微信的 mmkv

    // sqlite

    // green dao


    /**
     *
     * @param context
     */
    public static void initMMKV(Context context){
        String rootDir = MMKV.initialize(context);
        System.out.println("mmkv root: " + rootDir);
    }

}
