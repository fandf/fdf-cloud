package com.fdf.config;


/**
 * @author dongfengfan
 */
public class ConfigMd5 {
    private static String md5;

    public static String getMd5() {
        return md5;
    }

    public static void setMd5(String md5) {
        ConfigMd5.md5 = md5;
    }
}
