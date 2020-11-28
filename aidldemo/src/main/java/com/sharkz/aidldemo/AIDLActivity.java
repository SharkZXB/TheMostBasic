package com.sharkz.aidldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;

public class AIDLActivity extends AppCompatActivity {


    private static final String TAG = "AIDLActivity";

    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;

    private ILibraryManager mLibraryManager;

    /**
     * 消息处理的
     */
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Log.e(TAG, "new book:" + msg.obj);
                    break;
            }
            return true;
        }
    });

    /**
     * 接受服务端回调
     */
    private IOnNewBookArrivedListener listener = new IOnNewBookArrivedListener.Stub() {

        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            // 由于 onNewBookArrived 方法在子线程被调用，所以通过Handler切换到UI线程，方便UI操作
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, book).sendToTarget();
        }
    };

    /**
     *  Binder意外终止后，重新绑定服务
     */
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mLibraryManager != null) {
                mLibraryManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
                mLibraryManager = null;

                bindNewService();
            }
        }
    };

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 获取到 manager
            ILibraryManager libraryManager = ILibraryManager.Stub.asInterface(service);
            mLibraryManager = libraryManager;

            try {
                mLibraryManager.asBinder().linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            try {
                // 默认获取服务端的数据
                List<Book> books = libraryManager.getNewBookList();
                Log.e(TAG, "books:" + books.toString());
                // 客户端操作服务端添加数据
                libraryManager.donateBook(new Book("book" + books.size()));
                List<Book> books2 = libraryManager.getNewBookList();
                Log.e(TAG, "books:" + books2.toString());

                // 注册通知
                libraryManager.register(listener);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_i_d_l);

        bindNewService();
    }

    private void bindNewService() {
        Intent intent = new Intent(AIDLActivity.this, LibraryManagerService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);

        // TODO 如果客户端是另一个应用，需要隐式绑定服务
//        Intent intent = new Intent();
//        intent.setAction("android.intent.action.LibraryManagerService");
//        intent.setPackage("com.shh.ipctest");
//        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }


    @Override
    protected void onDestroy() {
        unbindService(mServiceConnection);
        if (mLibraryManager != null && mLibraryManager.asBinder().isBinderAlive()) {
            try {
                // 取消注册
                mLibraryManager.unregister(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }
}