package com.sharkz.tool.kit.encrypt;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * MD5加密工具类
 */

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020-02-11  14:04
 * 描    述  MD5即Message-Digest Algorithm 5（信息-摘要算法5），用于确保信息传输完整一致。
 *          是计算机广泛使用的杂凑算法之一（又译摘要算法、哈希算法），主流编程语言普遍已有MD5实现。
 *          将数据（如汉字）运算为另一固定长度值，是杂凑算法的基础原理，MD5的前身有MD2、MD3和MD4。
 *          MD5本质是一种散列函数，用以提供消息的完整性保护。单项的加密，不能解密，长度固定（32位）。
 *          通过对任意长度的信息逐位进行计算，产生一个二进制长度为128位（十六进制长度就是32位）的 hash 值,
 *          不同的文件产生相同的hash的可能性是非常小。
 * 修订历史：
 * ================================================
 */
public final class Md5Tool {

    private Md5Tool() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取文件的MD5值
     *
     * @param file
     * @return
     */
    public static String getFileMD5(File file) throws Exception {
        if (file == null || !file.exists()) {
            return "";
        }
        FileInputStream fis = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            return bytes2Hex(digest.digest());
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * 一个byte转为2个hex字符
     *
     * @param src byte数组
     * @return 16进制大写字符串
     */
    private static String bytes2Hex(byte[] src) {
        char[] res = new char[src.length << 1];
        final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        for (int i = 0, j = 0; i < src.length; i++) {
            res[j++] = hexDigits[src[i] >>> 4 & 0x0F];
            res[j++] = hexDigits[src[i] & 0x0F];
        }
        return new String(res);
    }

    /**
     * 字符串MD5
     * @param s 需要加密的字符串
     * @return 加密过后的字符串
     */
    public static String string2MD5(String s) {
        if (TextUtils.isEmpty(s)) {
            return "";
        }
        try {
            //初始化MessageDigest对象
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            //（可选）调用reset方法重置要传入进行加密的数据
            messageDigest.reset();
            //调用update方法传入要进行加密的byte数据
            messageDigest.update(s.getBytes("UTF-8"));
            //调用digest方法对传入的数据进行加密
            byte[] byteArray = messageDigest.digest();
            // java中一个byte为8个二进制位,16*8=128
            //Log.e("xyh", "md5: " + byteArray.length);  //16
            StringBuffer md5StrBuff = new StringBuffer();
            for (int i = 0; i < byteArray.length; ++i) {
                //转换为16进制,0xFF也可以写成255
                String hexString = Integer.toHexString(0xFF & byteArray[i]);
                if (hexString.length() == 1) {
                    md5StrBuff.append("0").append(hexString);
                } else {
                    md5StrBuff.append(hexString);
                }
            }
            return md5StrBuff.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 字节MD5加密
     * @param target 字节
     * @param len 长度
     * @return
     */
    public static String byte2MD5(byte[] target, int len) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(target, 0, len);
            byte[] byteArray = messageDigest.digest();
            StringBuffer md5StrBuff = new StringBuffer();
            for (int i = 0; i < byteArray.length; ++i) {
                if (Integer.toHexString(255 & byteArray[i]).length() == 1) {
                    md5StrBuff.append("0").append(Integer.toHexString(255 & byteArray[i]));
                } else {
                    md5StrBuff.append(Integer.toHexString(255 & byteArray[i]));
                }
            }

            return md5StrBuff.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取文件夹的MD5
     *
     * @param file
     * @param listChild
     * @return
     */
    public static Map<String, String> getDirMD5(File file, boolean listChild) throws Exception {
        if (!file.isDirectory()) {
            return null;
        } else {
            Map<String, String> map = new HashMap();
            File[] files = file.listFiles();

            for (int i = 0; i < files.length; ++i) {
                File f = files[i];
                if (f.isDirectory() && listChild) {
                    map.putAll(getDirMD5(f, listChild));
                } else {
                    String md5 = getFileMD5(f);
                    if (md5 != null) {
                        map.put(f.getPath(), md5);
                    }
                }
            }
            return map;
        }
    }
}

