package com.wangw.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    public static String code(String str) {
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");//应该是指定MD5加密方法？
            md.update(str.getBytes());// what for?
            byte[] byteDigest = md.digest();//what for?
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i = byteDigest[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            return buf.toString(); //32位加密
            // return buf.toString().substring(8,24) //16位加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(code("123456"));
    }
}
