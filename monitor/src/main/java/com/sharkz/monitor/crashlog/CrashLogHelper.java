package com.sharkz.monitor.crashlog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/6/16  15:59
 * 描    述
 * 修订历史：
 * ================================================
 */
public class CrashLogHelper {

    private int mPId;
    private String cmds;

    private static ExecutorService singlePool = Executors.newSingleThreadExecutor();

//    private static class SINGLE {
//        private static LogHelper logHelper = new LogHelper();
//    }

    public CrashLogHelper() {
        mPId = android.os.Process.myPid();
        cmds = "logcat  | grep \"(" + mPId + ")\"";//打印所有日志信息;
    }

//    public static LogHelper getInstance() {
//        return SINGLE.logHelper;
//    }

    public void start() {
        singlePool.execute(new Runnable() {
            @Override
            public void run() {
                Process logcatProc = null;
                BufferedReader mReader = null;
                try {
                    logcatProc = Runtime.getRuntime().exec(cmds);
                    mReader = new BufferedReader(new InputStreamReader(logcatProc.getInputStream()), 1024);
                    String line = null;

                    while ((line = mReader.readLine()) != null) {
                        if (line.length() == 0) {
                            continue;
                        }
                        if (!CrashLogConfig.getInstance().getIsWriteLog()){
                            continue;
                        }
                        if (line.contains(String.valueOf(mPId))) {
                            CrashLogFileUtils.writeLogFile((line + "\n"), CrashLogConfig.getInstance().getFileName());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (logcatProc != null) {
                        logcatProc.destroy();
                        logcatProc = null;
                    }
                    if (mReader != null) {
                        try {
                            mReader.close();
                            mReader = null;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

}
