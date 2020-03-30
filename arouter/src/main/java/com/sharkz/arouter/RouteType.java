package com.sharkz.arouter;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020-02-24  17:22
 * 描    述 用于@Route extras 字段数据
 * 修订历史：
 * ================================================
 */
@IntDef(flag = true, value = {
        RouteType.GreenChannel,
        RouteType.Fragment,
        RouteType.Activity,
        RouteType.Service,
        RouteType.WithinTitlebar,
        RouteType.TitlebarFragment,
        RouteType.Login,
        RouteType.Main,
        RouteType.LoginFragment,
        RouteType.LoginActivity,
        RouteType.MainFragment,
        RouteType.GreenService,
        RouteType.MainActivity
})
@Retention(RetentionPolicy.CLASS)
public @interface RouteType {


    // 可以自定义字段 下面的是栗子

    int GreenChannel = 1;
    int Fragment = 1 << 1;
    int Activity = 1 << 2;
    int Service = 1 << 3;
    int WithinTitlebar = 1 << 4;
    int Login = 1 << 5;
    int Main = 1 << 6;

    int TitlebarFragment = Fragment | WithinTitlebar;
    int LoginFragment = Fragment | Login;
    int MainFragment = Fragment | Main;
    int MainActivity = Activity | Main;
    int LoginActivity = Activity | Login;
    int GreenService = Service | GreenChannel;


}
