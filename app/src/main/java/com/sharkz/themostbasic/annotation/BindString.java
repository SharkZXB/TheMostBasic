package com.sharkz.themostbasic.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/4/27  10:21
 * 描    述
 * 修订历史：
 * ================================================
 */
@Retention(RetentionPolicy.RUNTIME)     // 生命周期
@Target(ElementType.FIELD)              // 注解出现的地方
public @interface BindString {
}
