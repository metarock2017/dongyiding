package org.redrock.menu;

import org.redrock.component.Support;
import org.redrock.util.CurlUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    public static void main(String[] args) {
        Menu menu = new Menu();

        Button button1 = new Button();
        button1.setType("click");
        button1.setName("今日歌曲");
        button1.setKey("V1001_TODAY_MUSIC");

        Button button2 = new Button();
        button2.setName("菜单");

        Button button3 = new Button();
        button3.setType("click");
        button3.setName("搜索");
        button3.setUrl("http://jx3536.s1.natapp.link/test");

        button2.addButton(button3);
        menu.addButton(button1);
        menu.addButton(button2);

        String result = null;
        try {
            result = updateMenu(menu);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }

    public static String updateMenu(Menu menu) throws JSONException {
        String accessToken = Support.getAccessToken();
        System.out.println(accessToken);
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken;
        String menuData = menu.toJson();
        return CurlUtil.postData(url, menuData);
    }

    private List<Button> button;

    public String toJson() throws JSONException {
        JSONArray buttonData = new JSONArray();
        JSONObject menuData = new JSONObject();
        for (int i = 0; i < button.size(); i++) {
            Button b = button.get(i);
            buttonData.put(i, b.toJson());
        }
        menuData.put("button", buttonData);
        return menuData.toString();
    }

    public void addButton(Button button) {
        if (this.button == null) {
            this.button = new ArrayList<>();
        }
        this.button.add(button);
    }
}
