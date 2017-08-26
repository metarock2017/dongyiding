//package org.redrock.QRcode;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.redrock.component.Support;
//import org.redrock.util.CurlUtil;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class GetQRcode {
//    public static Map<String, Object> CreateTicket() throws JSONException {
//        String accessToken = Support.getAccessToken();
//        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accessToken;
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("action_info", "action_info");
//        params.put("action_name", "QR_STR_SCENE");
//        params.put("scene_str", "test");
//        params.put("expire_seconds", 2592000);
//
//        String jsonStr = CurlUtil.getContent(url, params, "POST");
//        Map<String, Object> result = new HashMap<>();
//        JSONObject json = new JSONObject(jsonStr);
//        String ticket = json.getString("ticket");
//        result.put("ticket", ticket);
//        return result;
//    }
//
//    public void GetQRcode() {
//        Map<String, Object> param = new HashMap<>();
//
//        String url = "https://mp.weixin.qq.com/cmgi-bin/showqrcode?ticket=TICKET";
//        String jsonStr = CurlUtil.getContent(url, param, "GET");
//
//    }
//}