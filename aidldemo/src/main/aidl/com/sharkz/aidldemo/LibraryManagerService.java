package com.sharkz.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static android.os.Binder.getCallingUid;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/9/3  11:04
 * 描    述
 * 修订历史：
 * ================================================
 */
public class LibraryManagerService extends Service {


    private static final String TAG = "LibraryManagerService";

    /**
     * CopyOnWriteArrayList 支持并发读写 用于保存Book的集合
     */
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

    /**
     * 系统提供的专门用于删除跨进程 listener 的类
     * 远程回调
     * 客户端注册当前监听器 服务端调用 回调给客户端处理
     */
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<>();

    /**
     * AtomicBoolean 支持并发读写
     */
    private AtomicBoolean mIsServiceDestroy = new AtomicBoolean(false);

    /**
     * public static abstract class Stub extends android.os.Binder implements com.sharkz.aidldemo.ILibraryManager
     * Stub 是继承android.os.Binder的
     */
    private Binder mBinder = new ILibraryManager.Stub() {

        @Override
        public List<Book> getNewBookList() throws RemoteException {
            // 从服务端获取数据 服务端-->数据-->客户端
            return mBookList;
        }

        @Override
        public void donateBook(Book book) throws RemoteException {
            // 客户端发送数据 客户端-->数据-->服务端
            mBookList.add(book);
        }

        @Override
        public void register(IOnNewBookArrivedListener listener) throws RemoteException {
            // 客户端注册的监听器 服务端调用 客户端具体处理
            mListenerList.register(listener);
        }

        @Override
        public void unregister(IOnNewBookArrivedListener listener) throws RemoteException {
            // 用完了要释放
            mListenerList.unregister(listener);
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            // 客户端调用服务端方法时校验，客户端和服务端是否是同一个应用都可以验证
            if (!permissionCheck()) {
                Log.e(TAG, "transact denied");
                return false;
            }
            return super.onTransact(code, data, reply, flags);
        }
    };


    // =============================================================================================


    public LibraryManagerService() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // 客户端绑定服务端时校验
        // 如果客户端和服务端是两个应用，则无法在onBind中完成校验，需要在onTransact中完成
//        if (!permissionCheck()) {
//            Log.e(TAG, "bind denied");
//            return null;
//        }
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 默认添加两本书
        mBookList.add(new Book("book0"));
        mBookList.add(new Book("book1"));

        // 在子线程中每隔3秒创建一本新书，并通知所有已注册的客户端
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 如果服务还没终止
                while (!mIsServiceDestroy.get()) {
                    try {
                        Thread.sleep(3 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Book book = new Book("book" + mBookList.size());
                    mBookList.add(book);
                    bookArrivedNotify(book);
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServiceDestroy.set(true);
    }

    // =============================================================================================

    /**
     * 服务端通知 客户端
     *
     * @param book
     */
    private void bookArrivedNotify(Book book) {
        // 开始
        int n = mListenerList.beginBroadcast();
        for (int i = 0; i < n; i++) {
            IOnNewBookArrivedListener listener = mListenerList.getBroadcastItem(i);
            if (listener != null) {
                try {
                    // 服务端调用 客户端处理
                    listener.onNewBookArrived(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        // 结束
        mListenerList.finishBroadcast();
    }

    /**
     * 进行客户端的连接校验
     *
     * @return
     */
    private boolean permissionCheck() {
        // 客户端是否已申请了指定权限
        int check = checkCallingOrSelfPermission("com.sharkz.aidldemo.permission.ACCESS_LIBRARY_SERVICE");
        if (check == PackageManager.PERMISSION_DENIED) {
            return false;
        }

        // 检验客户端包名 只有指定的客户端才可以连接
        String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
        if (packages != null && packages.length > 0 && !packages[0].startsWith("com.sharkz")) {
            return false;
        }

        return true;
    }

}
