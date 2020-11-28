// IOnNewBookArrivedListener.aidl
package com.sharkz.aidldemo;

import com.sharkz.aidldemo.Book;

// Declare any non-default types here with import statements

// 这个接口用于 服务端调用客户端 注册的监听接口
interface IOnNewBookArrivedListener {

       /**
        * Demonstrates some basic types that you can use as parameters
        * and return values in AIDL.
        */
       void onNewBookArrived(in Book book);
}
