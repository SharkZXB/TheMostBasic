package com.sharkz.arouter.sdk;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;


/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2019/11/1
 * 描    述：全局路由拦截器 测试
 * 修订历史：
 * ================================================
 */
@Interceptor(priority = 7)
public class ARouterInterceptor implements IInterceptor {

    private Context mContext;

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {

        // 测试 登录跳转回调
        if (ARouterConstant.TestModule.PATH_TestARouterActivity.equals(postcard.getPath())) {
            callback.onInterrupt(null);
            // 模拟需要登录 登录完成之后回来 继续 当前界面的跳转
            ARouterManager.navigation(ARouterConstant.Login.PATH_LOGIN);
        } else {
            callback.onContinue(postcard);
        }
    }

    @Override
    public void init(Context context) {
        mContext = context;
        Log.e("testService", ARouterInterceptor.class.getName() + " has init.");
    }
}
