package com.sharkz.themostbasic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import com.sharkz.tool.kit.network.NetWorkTool;
import com.sharkz.tool.kit.network.WIFISignalBroadcastReceiver;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/3/30  21:35
 * 描    述
 * 修订历史：
 * ================================================
 */
public class MainActivity extends AppCompatActivity {


    public TextView mTextView;
    public TelephonyManager mTelephonyManager;
    public PhoneStatListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView=findViewById(R.id.tv);
        //获取telephonyManager
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //开始监听
        mListener = new PhoneStatListener();
        //监听信号强度
        mTelephonyManager.listen(mListener, PhoneStatListener.LISTEN_SIGNAL_STRENGTHS);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Listen for changes to the network signal strengths (cellular).
        mTelephonyManager.listen(mListener, PhoneStatListener.LISTEN_SIGNAL_STRENGTHS);


        WIFISignalBroadcastReceiver.registerWIFISignalBroadcastReceiver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //用户不在当前页面时，停止监听
        mTelephonyManager.listen(mListener, PhoneStatListener.LISTEN_NONE);

        WIFISignalBroadcastReceiver.unregisterReceiver(this);

    }

    @SuppressWarnings("deprecation")
    private class PhoneStatListener extends PhoneStateListener {
        //获取信号强度

        @Override
        public void onSignalStrengthChanged(int asu) {
            super.onSignalStrengthChanged(asu);
            System.out.println("onSignalStrengthChanged(int asu)");
        }

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            //获取网络信号强度
            //获取0-4的5种信号级别，越大信号越好,但是api23开始才能用
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                int level = signalStrength.getLevel();
                System.out.println("level====" + level);
            }

            int cdmaDbm = signalStrength.getCdmaDbm();
            int evdoDbm = signalStrength.getEvdoDbm();
            System.out.println("cdmaDbm=====" + cdmaDbm);
            System.out.println("evdoDbm=====" + evdoDbm);

            int gsmSignalStrength = signalStrength.getGsmSignalStrength();
            int dbm = -113 + 2 * gsmSignalStrength;
            System.out.println("dbm===========" + dbm);

            //获取网络类型
            int netWorkType = NetWorkTool.getNetworkState(MainActivity.this);
            switch (netWorkType) {
                case NetWorkTool.NETWORK_WIFI:
                    mTextView.setText("当前网络为wifi,信号强度为：" + gsmSignalStrength);
                    break;
                case NetWorkTool.NETWORK_2G:
                    mTextView.setText("当前网络为2G移动网络,信号强度为：" + gsmSignalStrength);
                    break;
                case NetWorkTool.NETWORK_3G:
                    mTextView.setText("当前网络为3G移动网络,信号强度为：" + gsmSignalStrength);
                    break;
                case NetWorkTool.NETWORK_4G:
                    mTextView.setText("当前网络为4G移动网络,信号强度为：" + gsmSignalStrength);
                    break;
                case NetWorkTool.NETWORK_NONE:
                    mTextView.setText("当前没有网络,信号强度为：" + gsmSignalStrength);
                    break;
                case -1:
                    mTextView.setText("当前网络错误,信号强度为：" + gsmSignalStrength);
                    break;
            }
        }
    }

}
