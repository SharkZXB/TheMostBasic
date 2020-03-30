package com.sharkz.arouter.sdk;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.Map;


/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2019/10/31
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ARouterManager {

    /**
     * 简单的路由跳转
     *
     * @param path 路径
     */
    public static void navigation(String path) {
        ARouter.getInstance().build(path).navigation();
    }


    /**
     * 跳转ForResult
     */
    public static void forResult(String path, Activity activity, int requestCode) {
        ARouter.getInstance().build(path).navigation(activity, requestCode);
    }


    /**
     * 获取Fragment 实例
     */
    public static Fragment getFragment(String path) {
        return (Fragment) ARouter.getInstance().build(path).navigation();
    }

    /**
     * 携带参数的应用内跳转  遍历 map
     */
    public static void normalNavigationWithParams(String path, Map<String, Object> map) {
        ARouter.getInstance().build(path)
                .withString("key1", "value1")
                .navigation();
    }


    /**
     * 新版本转场动画
     */
    public static void newVersionAnim(View view, String path) {

        if (Build.VERSION.SDK_INT >= 16) {

            // 动画
            ActivityOptionsCompat compat = ActivityOptionsCompat.
                    makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);

            ARouter.getInstance()
                    .build(path)
                    .withOptionsCompat(compat)
                    .navigation();
        } else {
            // Toast.makeText(this, "API < 16,不支持新版本动画", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 通过URL跳转
     */
    public static void navByUrl(String path, String url) {
        ARouter.getInstance()
                .build(path)
                .withString("url", url)
                .navigation();
    }

}
