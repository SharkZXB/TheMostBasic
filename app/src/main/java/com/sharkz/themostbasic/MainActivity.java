package com.sharkz.themostbasic;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.sharkz.themostbasic.reflect.ReflectClass;
import com.sharkz.tool.kit.TextTool;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/3/30  21:35
 * 描    述
 * 修订历史：
 * ================================================
 */
public class MainActivity extends AppCompatActivity {

    private static String TAG = "Shark";

    public TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.tv);
        TextTool.autoMatchFontSize(mTextView, "我是谁我是谁我是谁我是谁我是谁我是谁我是谁");


        try {
            // 创建对象
            ReflectClass.reflectNewInstance();
            // 反射私有的构造方法
            ReflectClass.reflectPrivateConstructor();
            // 反射私有属性
            ReflectClass.reflectPrivateField();
            // 反射私有方法
            ReflectClass.reflectPrivateMethod();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.d(TAG," zenmode = " + ReflectClass.getZenMode());
    }


}
