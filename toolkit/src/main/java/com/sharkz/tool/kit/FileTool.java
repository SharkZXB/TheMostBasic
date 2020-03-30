package com.sharkz.tool.kit;

import java.io.File;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/2/29  12:16
 * 描    述 当前是文件操作的工具类
 * 修订历史：
 * ================================================
 */
public class FileTool {

    /**
     * 删除文件
     *
     * @param filePath
     */
    public static void deleteFile(String filePath) {
        if (filePath == null) {
            return;
        }
        File file = new File(filePath);
        try {
            if ((file.isFile())) {
                file.delete();
            }
        } catch (Exception e) {

        }
    }

    /**
     * 删除文件夹的所有文件
     *
     * @param file
     * @return
     */
    public static boolean delAllFile(File file) {
        if (file == null || !file.exists()) {
            return false;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null)
                for (File f : files) {
                    delAllFile(f);
                }
        }
        return file.delete();
    }

}
