package com.sharkz.themostbasic;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/4/23  14:20
 * 描    述 自定义异常
 * 修订历史：
 * ================================================
 */
public class CustomException extends Exception {

    public CustomException(){
        super();
    }

    public CustomException(String msg){
        super(msg);
    }

    public CustomException(Throwable throwable){
        super(throwable);
    }

    public CustomException(String mag,Throwable throwable){
        super(mag,throwable);
    }

}
