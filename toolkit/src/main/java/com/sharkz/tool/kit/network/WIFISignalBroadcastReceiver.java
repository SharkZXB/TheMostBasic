package com.sharkz.tool.kit.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.widget.Toast;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/4/22  17:39
 * 描    述 监听Wi-Fi信号强度的广播
 * 修订历史：
 * ================================================
 */
public class WIFISignalBroadcastReceiver extends BroadcastReceiver {

    private static WIFISignalBroadcastReceiver mMyReceiver;
    private static final String NETWORK_STATE_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";

    /**
     * 注册广播
     *
     * @param context 上下文
     */
    public static void registerWIFISignalBroadcastReceiver(Context context) {
        if (mMyReceiver != null)
            return;
        mMyReceiver = new WIFISignalBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
       // filter.addAction(NETWORK_STATE_CHANGE);                     //
       // filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);    //
        filter.addAction(WifiManager.RSSI_CHANGED_ACTION);          // 信号强度
        context.registerReceiver(mMyReceiver, filter);
    }

    /**
     * 注销广播
     *
     * @param context  上下文
     */
    public static void unregisterReceiver(Context context) {
        if (mMyReceiver == null)
            return;
        context.unregisterReceiver(mMyReceiver);
        mMyReceiver = null;
    }

    // =============================================================================================

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

//        // Wi-Fi状态修改
//        if (action.equals(NETWORK_STATE_CHANGE)) {
//          //  boolean isNetOK = NetWorkTool.isNetConnected();
//           // Logcat.d(TAG, "网络状态发生变化,是否可用：" + isNetOK);
////            if (isNetOK) {
////                initWifiState();
////            } else {
////                btnWifi.setBackgroundResource(R.drawable.selector_wifi_0);
////            }
//
//            //
//        } else if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
//            int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);
//
////            if (wifistate == WifiManager.WIFI_STATE_DISABLED) {
////                btnWifi.setBackgroundResource(R.drawable.selector_wifi_0);
////            } else if (wifistate == WifiManager.WIFI_STATE_ENABLED) {
////                updateWifiStrength();
////            }
//
//            //
//        } else
            if (action.equals(WifiManager.RSSI_CHANGED_ACTION)) {
           // initWifiState();
            Toast.makeText(context, "信号强度改变了 ", Toast.LENGTH_SHORT).show();

        }
    }

}
