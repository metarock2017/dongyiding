package org.redrock.menu;

import org.redrock.component.Support;
import org.redrock.util.Const;
import org.redrock.util.CurlUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.redrock.util.DBConnection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {
    public DBConnection dbConnection = new DBConnection();

    public static void main(String[] args) {
        Menu menu = new Menu();

        Button sub_button11 = new Button();
        sub_button11.setType("click");
        sub_button11.setName("谁是卧底");
        sub_button11.setKey("createroom");
        Button sub_button12 = new Button();
        sub_button12.setType("view");
        sub_button12.setName("游戏帮助");
        sub_button12.setUrl("http://hongyan.cqupt.edu.cn/");
        Button button1 = new Button();
        button1.setName("谁是卧底");

        Button sub_button21 = new Button();
        sub_button21.setType("view");
        sub_button21.setName("创建房间");
        sub_button21.setUrl("http://hongyan.cqupt.edu.cn/");
        Button sub_button22 = new Button();
        sub_button22.setType("view");
        sub_button22.setName("开始游戏");
        sub_button22.setUrl("http://hongyan.cqupt.edu.cn/");
        Button sub_button23 = new Button();
        sub_button23.setType("view");
        sub_button23.setName("管理房间");
        sub_button23.setUrl("http://hongyan.cqupt.edu.cn/");
        Button button2 = new Button();
        button2.setName("在线玩");

        Button sub_button31 = new Button();
        sub_button31.setType("view");
        sub_button31.setName("加入我们");
        sub_button31.setUrl("http://hongyan.cqupt.edu.cn/");
        Button sub_button32 = new Button();
        sub_button32.setType("view");
        sub_button32.setName("更多游戏");
        sub_button32.setUrl("http://hongyan.cqupt.edu.cn/");
        Button button3 = new Button();
        button3.setName("玩游戏");

        button1.addButton(sub_button11);
        button1.addButton(sub_button12);
        button2.addButton(sub_button21);
        button2.addButton(sub_button22);
        button2.addButton(sub_button23);
        button3.addButton(sub_button31);
        button3.addButton(sub_button32);
        menu.addButton(button1);
        menu.addButton(button2);
        menu.addButton(button3);

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
        System.out.println(menuData);
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
