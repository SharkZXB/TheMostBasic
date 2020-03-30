package com.sharkz.tool.kit;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.sharkz.tool.BuildConfig;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2019/12/20
 * 描    述：日志 全局使用
 * 修订历史：
 * ================================================
 */
public class LoggerTool {

    /**
     * 日志显示开关
     */
    private static boolean IS_DEBUG = true;

    private static LoggerTool loggerTool;

    public static LoggerTool getInstance() {
        if (loggerTool == null) {
            synchronized (LoggerTool.class) {
                if (loggerTool == null) {
                    loggerTool = new LoggerTool();
                }
            }
        }
        return loggerTool;
    }


    // =============================================================================================


    /**
     * 初始化
     */
    public void init() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder().build();
        // 修改默认配置
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

//        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
//                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
//                .methodCount(0)         // (Optional) How many method line to show. Default 2
//                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
//                .tag("My custom tag")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
//                .build();
//        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        // 可关闭的
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                // 可根据参数 priority、 tag 选择性的关闭打开日志
                return BuildConfig.DEBUG;
            }
        });

        // 写入到文件
        // CsvFormatStrategy formatStrategyDisk = CsvFormatStrategy.newBuilder().build();
        //Logger.addLogAdapter(new DiskLogAdapter(formatStrategyDisk));


    }



    // =============================================================================================


    public static void logASSERT(String tag,String msg){
        Logger.log(Logger.ASSERT,tag,msg,null);
    }

    public static void logERROR(String tag,String msg){
        Logger.log(Logger.ERROR,tag,msg,null);
    }

    public static void logWARN(String tag,String msg){
        Logger.log(Logger.WARN,tag,msg,null);
    }

    public static void logINFO(String tag,String msg){
        Logger.log(Logger.INFO,tag,msg,null);
    }

    public static void logDEBUG(String tag,String msg){
        Logger.log(Logger.DEBUG,tag,msg,null);
    }

    public static void logVERBOSE(String tag,String msg){
        Logger.log(Logger.VERBOSE,tag,msg,null);
    }


    // =============================================================================================


    public static void logASSERT(String msg){
        Logger.log(Logger.ASSERT,"PRETTY_LOGGER",msg,null);
    }

    public static void logERROR(String msg){
        Logger.log(Logger.ERROR,"PRETTY_LOGGER",msg,null);
    }

    public static void logWARN(String msg){
        Logger.log(Logger.WARN,"PRETTY_LOGGER",msg,null);
    }

    public static void logINFO(String msg){
        Logger.log(Logger.INFO,"PRETTY_LOGGER",msg,null);
    }

    public static void logDEBUG(String msg){
        Logger.log(Logger.DEBUG,"PRETTY_LOGGER",msg,null);
    }

    public static void logVERBOSE(String msg){
        Logger.log(Logger.VERBOSE,"PRETTY_LOGGER",msg,null);
    }

}

/*


    logger有7个可见等级，分别为

        SEVERE
        WARNING
        INFO
        CONFIG
        FINE
        FINER
        FINEST



    // 格式化给定的xml内容并将其打印出来
     public static void xml(@Nullable String xml) {
        printer.xml(xml);
      }

    // 格式化给定的json内容并打印它
    public static void json(@Nullable String json) {
        printer.json(json);
      }

    // 提示:在特殊情况下使用这个日志例如:意外的错误等
    public static void wtf(@NonNull String message, @Nullable Object... args) {
        printer.wtf(message, args);
      }

 private void LoggerExample() {
        //打印json
        Logger.json(JSON_CONTENT);
        //打印XML
        Logger.xml(XML_CONTENT);
        //List
        List<String> list = new ArrayList<>();
        list.add("haizhuo");
        list.add("001");
        Logger.d(list);
        //Map
        Map<String, Object> map = new HashMap<>();
        map.put("name", "haizhuo");
        map.put("index", 001);
        Logger.d(map);
        //Set
        Set<String> set = new HashSet<>();
        set.add("hello");
        set.add("world");
        Logger.d(set);
        //字符串格式化
        Logger.d("hello %s%d", "haizhuo", 1);

        //打印Exception
        int[] ints = new int[3];
        try {
            ints[3] = 12;
        } catch (Exception e) {
            Logger.e(e, "抛出异常");
        }

        //打印的TAG:全局设置的TAG-自定义tag举个例子本demo打印的TAG:LoggerDemo-LBW
        Logger.log(Logger.ERROR,"LBW","NB",null);
    }

*/

