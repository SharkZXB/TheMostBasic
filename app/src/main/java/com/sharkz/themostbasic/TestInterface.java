package com.sharkz.themostbasic;

import android.util.Log;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/8/20  14:08
 * 描    述
 * 修订历史：
 * ================================================
 */
public interface TestInterface extends InterfaceA,InterfaceB{

    //  因为默认是 static final 的 必须要初始化
    static int b=1;
    final int c=2;
    int a = 0;

    /**
     * JDK 1.8 之后 接口可以定义方法体了  直接调用使用
     */
    default void testBody() {
        Log.i("TAG", "testBody: ");
    }

    // 声明的函数默认都是 public abstract
    void testMethod();

}
