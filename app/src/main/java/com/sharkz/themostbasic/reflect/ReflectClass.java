package com.sharkz.themostbasic.reflect;

import android.content.Context;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/4/28  10:23
 * 描    述
 * 修订历史：
 * ================================================
 */
public class ReflectClass {

    private final static String TAG = "peter.log.ReflectClass";

    /**
     * 创建对象
     */
    public static void reflectNewInstance() {
        try {
            // 获取类类型
            Class<?> classBook = Class.forName("com.sharkz.themostbasic.reflect.Book");
            // 类类型创建实例
            Object objectBook = classBook.newInstance();
            // 类型转换
            Book book = (Book) objectBook;
            // 设置属性
            book.setName("Android进阶之光");
            book.setAuthor("刘望舒");
            // 验证数据
            Log.d(TAG, "reflectNewInstance book = " + book.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 反射私有的构造方法
     */
    public static void reflectPrivateConstructor() {
        try {
            // 获取类类型
            Class<?> classBook = Class.forName("com.sharkz.themostbasic.reflect.Book");
            // 获取带有执行参数的构造方法
            Constructor<?> declaredConstructorBook = classBook.getDeclaredConstructor(String.class, String.class);
            // 让我们在用反射时访问私有变量
            declaredConstructorBook.setAccessible(true);
            // 用构造方法创建实例
            Object objectBook = declaredConstructorBook.newInstance("Android开发艺术探索", "任玉刚");
            // 类型转换
            Book book = (Book) objectBook;
            // 验证数据
            Log.d(TAG, "reflectPrivateConstructor book = " + book.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Filed 反射私有属性
     */
    public static void reflectPrivateField() {
        try {
            // 获取类类型
            Class<?> classBook = Class.forName("com.sharkz.themostbasic.reflect.Book");
            // 类类型创建实例
            Object objectBook = classBook.newInstance();
            // 获取指定的当前类属性
            Field fieldTag = classBook.getDeclaredField("TAG");
            // 设置权限
            fieldTag.setAccessible(true);
            // 获取指定类属性 传入了实例
            String tag = (String) fieldTag.get(objectBook);
            // 验证数据
            Log.d(TAG, "reflectPrivateField tag = " + tag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method 反射私有方法
     */
    public static void reflectPrivateMethod() {
        try {
            // 获取类类型
            Class<?> classBook = Class.forName("com.sharkz.themostbasic.reflect.Book");
            // 获取指定函数并传入参数
            Method methodBook = classBook.getDeclaredMethod("declaredMethod", int.class);
            // 设置权限
            methodBook.setAccessible(true);
            // 类类型创建实例
            Object objectBook = classBook.newInstance();
            // 方法的调用
            String string = (String) methodBook.invoke(objectBook, 0);
            // 数据验证
            Log.d(TAG, "reflectPrivateMethod string = " + string);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获得系统Zenmode值
     *
     * @return
     */
    public static int getZenMode() {
        int zenMode = -1;
        try {
            Class<?> cServiceManager = Class.forName("android.os.ServiceManager");
            Method mGetService = cServiceManager.getMethod("getService", String.class);
            Object oNotificationManagerService = mGetService.invoke(null, Context.NOTIFICATION_SERVICE);
            Class<?> cINotificationManagerStub = Class.forName("android.app.INotificationManager$Stub");
            Method mAsInterface = cINotificationManagerStub.getMethod("asInterface", IBinder.class);
            Object oINotificationManager = mAsInterface.invoke(null, oNotificationManagerService);
            Method mGetZenMode = cINotificationManagerStub.getMethod("getZenMode");
            zenMode = (int) mGetZenMode.invoke(oINotificationManager);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return zenMode;
    }

    /**
     * 关闭手机
     */
    public static void shutDown() {
        try {
            Class<?> cServiceManager = Class.forName("android.os.ServiceManager");
            Method mGetService = cServiceManager.getMethod("getService", String.class);
            Object oPowerManagerService = mGetService.invoke(null, Context.POWER_SERVICE);
            Class<?> cIPowerManagerStub = Class.forName("android.os.IPowerManager$Stub");
            Method mShutdown = cIPowerManagerStub.getMethod("shutdown", boolean.class, String.class, boolean.class);
            Method mAsInterface = cIPowerManagerStub.getMethod("asInterface", IBinder.class);
            Object oIPowerManager = mAsInterface.invoke(null, oPowerManagerService);
            mShutdown.invoke(oIPowerManager, true, null, true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void shutdownOrReboot(final boolean shutdown, final boolean confirm) {
        try {
            Class<?> ServiceManager = Class.forName("android.os.ServiceManager");
            // 获得ServiceManager的getService方法
            Method getService = ServiceManager.getMethod("getService", java.lang.String.class);
            // 调用getService获取RemoteService
            Object oRemoteService = getService.invoke(null, Context.POWER_SERVICE);
            // 获得IPowerManager.Stub类
            Class<?> cStub = Class.forName("android.os.IPowerManager$Stub");
            // 获得asInterface方法
            Method asInterface = cStub.getMethod("asInterface", android.os.IBinder.class);
            // 调用asInterface方法获取IPowerManager对象
            Object oIPowerManager = asInterface.invoke(null, oRemoteService);
            if (shutdown) {
                // 获得shutdown()方法
                Method shutdownMethod = oIPowerManager.getClass().getMethod(
                        "shutdown", boolean.class, String.class, boolean.class);
                // 调用shutdown()方法
                shutdownMethod.invoke(oIPowerManager, confirm, null, false);
            } else {
                // 获得reboot()方法
                Method rebootMethod = oIPowerManager.getClass().getMethod("reboot",
                        boolean.class, String.class, boolean.class);
                // 调用reboot()方法
                rebootMethod.invoke(oIPowerManager, confirm, null, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
