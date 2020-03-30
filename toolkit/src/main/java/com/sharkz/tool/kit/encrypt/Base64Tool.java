package com.sharkz.tool.kit.encrypt;

import android.util.Base64;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020-02-11  12:23
 * 描    述  Base64严格地说，属于编码格式，而非加密算法,用于二进制内容转换为可编辑的文本内容。
 * Base64也会经常用作一个简单的“加密”来保护某些数据，而真正的加密通常都比较繁琐。
 * 编码后的内容，是由64个字符（大小写英文字母 0-9 + / (= 补位符，填充字符)）组成的序列，成为Base64。可逆的编码方式。
 * 常见于邮件、http加密，截取http信息，你就会发现登录操作的用户名、密码字段通过BASE64加密的。
 * 修订历史：
 * ================================================
 */
public class Base64Tool {


    /**
     * Base64 字符串加密
     */
    public static String encodeToString(String string) {
        return Base64.encodeToString(string.getBytes(), Base64.NO_WRAP);
    }


}
