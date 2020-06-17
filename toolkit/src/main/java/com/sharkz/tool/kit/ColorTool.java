package com.sharkz.tool.kit;

import android.graphics.Color;
import android.text.TextUtils;
import android.widget.ImageView;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/18  13:37
 * 描    述
 * 修订历史：
 * ================================================
 */
public class ColorTool {

    /**
     * 设置 ImageView 的滤镜 统一处理异常
     */
    public static void setColorFilter(ImageView imageView, String strColor) {
        if (imageView == null || TextUtils.isEmpty(strColor)) {
            return;
        }
        try {
            int parseColor = Color.parseColor(strColor);
            // 解析成功设置数据
            imageView.setColorFilter(parseColor);
        } catch (Exception e) {
            // 解析异常不处理 这里可以设置一个上报功能
            // 打印异常
            e.printStackTrace();
        }
    }


}
