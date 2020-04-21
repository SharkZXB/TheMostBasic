package com.sharkz.smallestwidth;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2019/12/24
 * 描    述：Android 最上层Application
 * 修订历史：
 * ================================================
 */
public class TopLevelApplication extends Application {

    /**
     * 下面两个属性 全局调用 App 的生命周期
     */
    public static TopLevelApplication application;
    public static Context appContext;


    // =============================================================================================


    /**
     * 得到应用上下文的Context，在应用创建时会首先调用；
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    /**
     * 同样在应用创建时调用，但比attachBaseContext()要晚；
     */
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        appContext = application.getApplicationContext();
    }

    /**
     * 配置改变时触发这个方法，例如手机屏幕旋转等。
     */
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 系统低内存时调用；
     * 但后台程序已经终止且资源还匮乏时候回调用这个方法。
     * 好的应用程序一般会在这个方法中释放一些不必要的资源来应付当后台程序已经终止、前台应用程序内存还是不够时的情况。
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    /**
     * 当终止应用程序对象时调用，不保证一定被调用，当程序被内核终止以便为其他应用程序释放资源时将不会提示，
     * 并且不调用应用程序对象的 onTerminate 方法而直接终止程序。
     */
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }


    /**
     * This method is for use in emulated process environments.  It will
     * never be called on a production Android device, where processes are
     * removed by simply killing them; no user code (including this callback)
     * is executed when doing so.
     * 真相大白了，这段文档明确的解释说，Android中Application的onTerminate()函数只是用来在Android设备的模拟器中，
     * 如果application退出才会回调。但是，在产品级（即运行在Android真机设备）应用App，
     * 不会再整个App退出时候回调这个onTerminate()函数。然而试想，又有谁会开发一个App目标运行设备仅仅是模拟器而不是真机呢？
     * 开发一个App肯定是打算运行在真实的Android设备上的啊，所以，在真实的Android机器上跑，
     * application的onTerminate()就是一个废物，不会被触发，也就是无效的回调函数。
     * 开发者不要错误的使用onTerminate()，想当然的认为在Android真机设备上，onTerminate()会在App应用退出时候回调和触发。
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
