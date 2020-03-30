package com.sharkz.arouter.sdk;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2019/10/31
 * 描    述：全局路由 界面路径常量池
 * 修订历史：
 * ================================================
 */
public abstract class ARouterConstant {

    public static final class TestModule {
        private static final String BASE = "/TestModule";
        public static final String PATH_TestARouterActivity = BASE + "/TestARouterActivity";
        public static final String PATH_Test2ARouterActivity = BASE + "/Test2ARouterActivity";
    }

    public static final class Login {
        public static final String PATH_LOGIN = "/Login/TestLoginActivity";
    }

}
