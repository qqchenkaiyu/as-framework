package cn.witsky.bs.basicframework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class SignUtils {

    private static Logger logger = LoggerFactory.getLogger(SignUtils.class);
    public static String signature(Map<String, Object> params, String secret, String signName) {
        String result = null;
        if (params == null)
            return result;

        // 1. 把字典按Key的字母顺序排序
        TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
        treeMap.putAll(params);

        // 2. remove sign parameter
        treeMap.remove(signName);

        return signature(treeMap,secret);
    }
    public static String signature(Map<String, Object> params, String secret) {
        String result = null;
        if (params == null)
            return result;
        // 1. 把字典按Key的字母顺序排序
        TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
        treeMap.putAll(params);
        String signature = signature(treeMap, secret);
        return signature;
    }
    public static String signature(TreeMap<String, Object> treeMap, String secret) {
        String result = null;
        if (treeMap == null)
            return result;
        // 3 把参数串起来
        Iterator<String> iter = treeMap.keySet().iterator();
        StringBuffer orgin = new StringBuffer(secret);
        while (iter.hasNext()) {
            String name = (String) iter.next();
            orgin.append(name).append(treeMap.get(name));
        }

        // 4. 加密：MD5 + 大写&十六进制
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byte2hex(md.digest(orgin.toString().getBytes("utf-8")));
            logger.debug("加密前："+orgin+"\n加密后："+result);
        } catch (Exception e) {
            throw new RuntimeException("sign error !");
        }

        return result;
    }

    public static String MTsignature(TreeMap<String, Object> treeMap, String secret) {
        String result = null;
        if (treeMap == null)
            return result;
        // 3 把参数串起来
        Iterator<String> iter = treeMap.keySet().iterator();
        StringBuffer orgin = new StringBuffer();
        while (iter.hasNext()) {
            String name = (String) iter.next();
            orgin.append(name).append("=").append(treeMap.get(name)).append("&");
        }
        orgin.append("secret=").append(secret);
        // 4. 加密：MD5 + 大写&十六进制
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byte2hex(md.digest(orgin.toString().getBytes("utf-8")));
            logger.debug("加密前："+orgin+"\n加密后："+result);
        } catch (Exception e) {
            throw new RuntimeException("sign error !");
        }

        return result;
    }

    public static String md(String body){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return byte2hex(md.digest(body.getBytes("utf-8")));
        } catch (Exception e) {

        }
        return "";
    }

    private static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs.append("0").append(stmp);
            else
                hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }

}
