package com.sharkz.tool.kit;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * ================================================
 * 作    者：SharkZ
 * 版    本：1.0
 * 创建日期：2019/12/12
 * 描    述：Json
 * 修订历史：
 * ================================================
 */
public class JsonTool {

    /**
     * 读取json文件,将整个json文件作为字符串形式返回
     *
     * @param fileName json文件名(带后缀)
     * @param context  上下文
     * @return json文件内容的字符串形式
     */
    public static String getJsonFile(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


//    /***
//     * object 转成json
//     * @param root
//     * @return
//     */
//    public static String convertObjectToJson(Object root) {
//
//        String resutlString = "";
//        try {
//            resutlString = JSON.toJSONString(root);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return resutlString;
//    }
//
//
//    /***
//     * json转成object
//     * @param
//     * @return
//     */
//    public static<T> T convertJsonToObject(String json,Class<T> clazz) {
//        T readValue = null;
//        try {
//            readValue = JSON.parseObject(json, clazz);
//        } catch (Exception e) {
//        }
//
//        return readValue;
//    }
//
//
//
//    /**
//     * 功能描述：把JSON数据转换成普通字符串列表
//     *
//     * @param jsonData
//     *            JSON数据
//     *
//     * @return
//     * @throws Exception
//     *
//     */
//    public static List<String> convertJsonToObjectList(String jsonData) throws Exception {
//        return JSON.parseArray(jsonData, String.class);
//    }
//
//    /**
//     * 功能描述：把JSON数据转换成jsonObject
//     *
//     * @param jsonData
//     *            JSON数据
//     * @return
//     * @throws Exception
//     *
//     */
//    public static JSONObject convertJsonToObject(String jsonData) throws Exception {
//        return JSON.parseObject(jsonData);
//    }
//
//
//    /**
//     * 功能描述：把JSON数据转换成指定的java对象列表
//     *
//     * @param json
//     *            JSON数据
//     * @param clazz
//     *            指定的java对象
//     * @return
//     * @throws Exception
//     */
//    public static <T> List<T> convertJsonToObjectObjList(String json, Class<T> clazz)
//            throws Exception {
//        return JSON.parseArray(json, clazz);
//    }
//
//    /**
//     * 功能描述：把JSON数据转换成较为复杂的java对象列表
//     *
//     * @param json
//     *            JSON数据
//     * @return
//     * @throws Exception
//     */
//    public static List<Map<String, Object>> convertJsonToObjectMapList(String json)
//            throws Exception {
//        return JSON.parseObject(json,
//                new TypeReference<List<Map<String, Object>>>() {
//                });
//    }
//
//
//    /**
//     * 将网络请求下来的数据用fastjson处理空的情况，并将时间戳转化为标准时间格式
//     * @param result
//     * @return
//     */
//    public static String dealResponseResult(String result) {
//        result = JSONObject.toJSONString(result,
//                SerializerFeature.WriteClassName,
//                SerializerFeature.WriteMapNullValue,
//                SerializerFeature.WriteNullBooleanAsFalse,
//                SerializerFeature.WriteNullListAsEmpty,
//                SerializerFeature.WriteNullNumberAsZero,
//                SerializerFeature.WriteNullStringAsEmpty,
//                SerializerFeature.WriteDateUseDateFormat,
//                SerializerFeature.WriteEnumUsingToString,
//                SerializerFeature.WriteSlashAsSpecial,
//                SerializerFeature.WriteTabAsSpecial);
//        return result;
//    }

}
