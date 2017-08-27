package org.redrock.component;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import org.redrock.util.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jx on 2017/7/21.
 */
public class Support {

    private static final String FORMAT = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";

    public static void bindServer(HttpServletRequest request, HttpServletResponse response) {
        try {
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String signature = request.getParameter("signature");
            String echostr = request.getParameter("echostr");
            if (SignUtil.checkSignature(timestamp, nonce, signature, echostr)) {
                response.getWriter().print(echostr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getAccessToken() throws JSONException {
        Jedis jedis = JedisUtil.getJedis();
        String key = Const.KeyOfAccessToken;
        if (isInvalid(jedis, key)) {
            synchronized (Support.class) {
                if (isInvalid(jedis, key)) {
                    Map<String, Object> data = curlForAccessToken();
                    String accessToken = (String) data.get("accessToken");
                    int expires_in = (int) data.get("expiresIn");
                    long timestamp = System.currentTimeMillis() + expires_in * 1000;
                    jedis.hset(key, "accessToken", accessToken);
                    jedis.hset(key, "timestamp", timestamp + "");
                    jedis.expire(key, expires_in);
                }
            }
        }
        return jedis.hget(key, "accessToken");
    }

    private static boolean isInvalid(Jedis jedis, String key) {
        return !jedis.exists(key) ||
                StringUtil.hasBlank(jedis.hget(key, "accessToken"), jedis.hget(key, "timestamp")) ||
                Long.valueOf(jedis.hget(key, "timestamp")) < System.currentTimeMillis();
    }

    private static Map<String, Object> curlForAccessToken() throws JSONException {
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        Map<String, Object> params = new HashMap<>();
        params.put("appid", Const.AppId);
        params.put("secret", Const.AppSecret);
        params.put("grant_type", "client_credential");
        String jsonStr = CurlUtil.getContent(url, params, "GET");
        Map<String, Object> result = new HashMap<>();
        JSONObject json = new JSONObject(jsonStr);
        String accessToken = json.getString("access_token");
        int expiresIn = json.getInt("expires_in");
        result.put("accessToken", accessToken);
        result.put("expiresIn", expiresIn);
        return result;
    }

    private static boolean ifEncrypt(HttpServletRequest request) {
        String encryptType = request.getParameter("encrypt_type");
        return encryptType != null && "aes".equals(encryptType);
    }
}