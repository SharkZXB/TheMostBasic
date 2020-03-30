package com.sharkz.tool.kit;

import android.text.TextPaint;
import android.text.TextUtils;
import android.widget.TextView;


/**
 * ================================================
 * 作    者：SharkZ
 * 版    本：1.0
 * 创建日期：2019/12/12
 * 描    述：TextView 文本显示
 * 修订历史：
 * ================================================
 */
public class TextTool {

    /**
     * 显示最长的字符个数  中文2个字符 ，英文1个字符
     */
    public static String handleText(String str, int maxLen) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        int count = 0;
        int endIndex = 0;
        for (int i = 0; i < str.length(); i++) {
            char item = str.charAt(i);
            if (item < 128) {
                count = count + 1;
            } else {
                count = count + 2;
            }
            if (maxLen == count || (item >= 128 && maxLen + 1 == count)) {
                endIndex = i;
            }
        }
        if (count <= maxLen) {
            return str;
        } else {
            return str.substring(0, endIndex) + "...";
        }
    }


    /**
     * 计算出该TextView中文字的长度(像素)
     *
     * @param textView 显示文本的 View
     * @param text     需要显示的内容
     * @return 像素
     */
    public static int getTextViewLength(TextView textView, String text) {
        TextPaint paint = textView.getPaint();
        // 得到使用该paint写上text的时候,像素为多少
        int textLength = (int) paint.measureText(text);
        return textLength;
    }

}
