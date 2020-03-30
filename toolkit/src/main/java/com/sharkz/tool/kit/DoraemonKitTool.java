//package com.sharkz.tool.kit;
//
//import android.app.Application;
//import android.content.Context;
//
//import com.didichuxing.doraemonkit.DoraemonKit;
//import com.didichuxing.doraemonkit.kit.webdoor.WebDoorManager;
//
///**
// * ================================================
// * 作    者：SharkZ
// * 邮    箱：229153959@qq.com
// * 创建日期：2019/12/24
// * 描    述：https://github.com/didi/DoraemonKit
// * https://github.com/didi/DoraemonKit/blob/master/Doc/android_cn_guide.md
// * 修订历史：
// * ================================================
// */
//public class DoraemonKitTool {
//
//    /**
//     * 初始化
//     *
//     * @param application
//     */
//    public static void init(Application application) {
//        DoraemonKit.install(application);
//
//        // H5任意门功能需要，非必须
//        DoraemonKit.setWebDoorCallback(new WebDoorManager.WebDoorCallback() {
//            @Override
//            public void overrideUrlLoading(Context context, String s) {
//                // 使用自己的H5容器打开这个链接
//            }
//        });
//
//        //false:不显示入口icon 默认为true 隐藏了 悬浮按钮
//        //DoraemonKit.setAwaysShowMianIcon(false);
//    }
//
//    /**
//     * 直接调起工具面板
//     */
//    public static void showToolPanel() {
//        DoraemonKit.showToolPanel();
//    }
//
//
//}
