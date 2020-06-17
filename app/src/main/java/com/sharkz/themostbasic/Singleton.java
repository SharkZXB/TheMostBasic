package com.sharkz.themostbasic;

import java.io.Serializable;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/19  13:55
 * 描    述
 * 修订历史：
 * ================================================
 */
public class Singleton implements Serializable {

    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    private Singleton() {
    }

    public static Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    //防止序列化破坏单例模式
    // 在jdk中ObjectInputStream的类中有readUnshared（）方法，上面详细解释了原因。
    // 我简单描述一下，那就是如果被反序列化的对象的类存在readResolve这个方法，
    // 他会调用这个方法来返回一个“array”（我也不明白），然后浅拷贝一份，作为返回值，
    // 并且无视掉反序列化的值，即使那个字节码已经被解析。
    public Object readResolve() {
        return SingletonHolder.INSTANCE;
    }
}