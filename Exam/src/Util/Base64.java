package Util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;

public class Base64 {
    public static String getBase64(String str)
    {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }
}
