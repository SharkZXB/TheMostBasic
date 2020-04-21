//package com.sharkz.arouter.sdk;
//
//import android.os.Looper;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//
//import com.alibaba.android.arouter.facade.template.IProvider;
//import com.alibaba.android.arouter.launcher.ARouter;
//import com.alibaba.android.arouter.utils.TextUtils;
//
///**
// * ================================================
// * 作    者：SharkZ
// * 邮    箱：229153959@qq.com
// * 创建日期：2020/4/16  09:36
// * 描    述
// * 修订历史：
// * ================================================
// */
//public class ARouterServiceManager {
//
//    public static final String TAG = "ArouterServiceManager";
//    public static final String BUNDLE_KEY = "MG_BUNDLE_KEY";
//    public static final String ACTION_KEY = "action";
//
//    @Nullable
//    public static <T extends IProvider> T provide(Class<T> clz, String path) {
//        if (TextUtils.isEmpty(path)) {
//            return null;
//        }
//        IProvider provider = null;
//        try {
//            provider = (IProvider) ARouter.getInstance().build(path)
//                    .navigation();
//        } catch (Exception e) {
//            LogUtil.i(TAG, "没有获取到需要的服务，请检查");
//        }
//        return (T) provider;
//    }
//
//    @Nullable
//    public static <T extends IProvider> T provide(Class<T> clz) {
//        IProvider provider = null;
//        try {
//            provider = ARouter.getInstance().navigation(clz);
//        } catch (Exception e) {
//            LogUtil.i(TAG, "没有获取到需要的服务，请检查");
//        }
//        if (BaseApplicationContext.DEBUG && null == provider) {
//            if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
//                Toast.makeText(BaseApplicationContext.application, "没有获取到需要的服务，请检查--->" + clz.getSimpleName(), Toast.LENGTH_LONG).show();
//            }
//            LogUtil.e(TAG, "没有获取到需要的服务，请检查-->" + clz.getSimpleName());
//
//        }
//        return (T) provider;
//    }
//    //TODO 共用的方法，如获取用户信息。拿到用户service 获取用户信息
//
//}
