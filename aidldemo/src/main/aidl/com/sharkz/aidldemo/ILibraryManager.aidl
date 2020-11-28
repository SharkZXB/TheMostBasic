// ILibraryManager.aidl
package com.sharkz.aidldemo;

// Declare any non-default types here with import statements

// 注意，尽管ILibraryManager.aidl和Book在同一包中，还是需要显示的导入Book类。
// 除了基本类型数据外，其它类型的参数需要标注方向，可选的方向标识有：
import com.sharkz.aidldemo.Book;
import com.sharkz.aidldemo.IOnNewBookArrivedListener;

// 定义接口 用于服务端与客户端之间的 数据交互
// 客户端调用接口定义的函数
// 最后执行的是 服务端实现的函数
interface ILibraryManager {

    // List<Book> getNewBookList();
    List<Book> getNewBookList();

    // out：输出类型参数
    // inout：输入输出类型参数
    // in 代表参数为输入类型的
    void donateBook(in Book book);

    void register(IOnNewBookArrivedListener listener);

    void unregister(IOnNewBookArrivedListener listener);
}
