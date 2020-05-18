package com.sharkz.permission;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/5/15  09:14
 * 描    述 这个 Module 处理 Android 动态权限的
 * 修订历史：https://github.com/yanzhenjie/AndPermission
 * ================================================
 */
public class PermissionTool {

    // 使用 原理
    /*

    RequestExecutor --> AIDL --> 调用异步进程的服务 --> BridgeService --> 启动了 BridgeActivity
    注册了一个广播

    BridgeActivity --> 使用了 透明的主题 在这个Activity里面发起了 权限请求


    TODO 更多详细的使用见源码Demo
    private void requestPermission(@PermissionDef String... permissions) {

        AndPermission.with(this)// 传入权限请求来源
                .runtime() // 权限请求的类型
                .permission(permissions)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        toast(R.string.successfully);
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        toast(R.string.failure);
                        if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, permissions)) {
                            showSettingDialog(MainActivity.this, permissions);
                        }
                    }
                })
                .start();
    }



    */

}
