package com.sharkz.themostbasic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sharkz.tool.kit.DateTool;
import com.sharkz.tool.kit.LoggerTool;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoggerTool.getInstance().init();

        String today = DateTool.getToday(1085670000000L);
        LoggerTool.logERROR(today);
    }
}
