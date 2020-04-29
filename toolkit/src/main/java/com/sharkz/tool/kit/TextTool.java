package com.sharkz.tool.kit;

import android.content.Context;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;


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


    /**
     * 获取TextView 的高度
     *
     * @param context     上下文
     * @param text        显示的文本
     * @param textSize    字体的大小
     * @param deviceWidth 屏幕的宽度
     * @return tv 的高度
     */
    public static int getHeightORWidth(@Nullable Context context, @Nullable String text, @Nullable int textSize, @Nullable int deviceWidth, @Nullable boolean isH) {
        TextView textView = new TextView(context);
        textView.setText(text);
        // 如果没有以像素为单位给出textSize，请更改setTextSize()的第一个参数。
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(deviceWidth, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        if (isH) {
            return textView.getMeasuredHeight();
        } else {
            return textView.getMeasuredWidth();
        }
    }

    /**
     * 根据内容 自动调整字体的大小
     *
     * @param view 显示文本的控件
     * @param txt  显示的文本
     */
    public static void autoMatchFontSize(final TextView view, String txt) {
        // 显示文本
        view.setText(txt);
        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                float vWidth = v.getWidth();                // view的宽度
                float vHeight = v.getHeight();              // view的高度
                TextPaint paint = view.getPaint();          // 画笔
                String text = view.getText().toString();    // 文本内容
                float textLen = paint.measureText(text);    // 文本长度
                float oldSize = view.getTextSize();         // 字体大小
                // 能显示的下就不调整了 显示不下才调整
                if (vWidth < textLen && textLen != vWidth) {
                    float size = vWidth * oldSize / textLen;
                    // 设置字体大小时，必须以TypedValue.COMPLEX_UNIT_PX的类型设置，这是为了匹配view.getTextSize()，
                    // 因为这个方法得到的字体也是以px为单位的，而view.setTextSize(size)默认是以sp为单位
                    view.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
                }
            }
        });
    }

}
