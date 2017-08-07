package org.redrock.servlet;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import org.redrock.util.Const;
import org.redrock.util.EncryptUtil;
import org.redrock.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


//配置路由
//@WebServlet(name = "IndexServlet", value = "/")
public class IndexServlet extends HttpServlet {
    //post请求处理
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Map<String, String[]> params = request.getParameterMap();
//            for (String param : params.keySet()) {
//                System.out.println(param);
//            }
            String encodingAesKey = Const.EncodingAESKey;
            String token = Const.Token;
            String appId = Const.AppId;
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String msgSignature = request.getParameter("msg_signature");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            request.getInputStream(), "UTF-8"
                    )
            );
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String encryptMsg = builder.toString();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder1 = factory.newDocumentBuilder();
            Document document = builder1.parse(
                    new InputSource(
                            new StringReader(encryptMsg)
                    )
            );
            Element element = document.getDocumentElement();
            NodeList nodeList = element.getChildNodes();
            Map<String, String> result = new HashMap<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (!node.getNodeName().equals("#text")) {
                    String nName = node.getNodeName();
                    String nValue = node.getTextContent();
                    result.put(nName, nValue);
                }
            }
            String toUserName = result.get("ToUserName");
            String encrypt = result.get("Encrypt");
            String format = "<xml><ToUserName><![CDATA[%s]]></ToUserName><Encrypt><![CDATA[%s]]></Encrypt></xml>";
            String fromXML = String.format(format, toUserName, encrypt);
            WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
            String result2 = pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);
            //System.out.println("解密后明文: " + result2);

            DocumentBuilderFactory factory2 = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder2 = null;
            builder2 = factory2.newDocumentBuilder();
            Document document2 = builder2.parse(
                    new InputSource(new StringReader(result2))
            );

            //获取根节点
            Element rootNode = document2.getDocumentElement();
            //根节点名
            String name = rootNode.getNodeName();
            //获取子节点数组
            NodeList items = rootNode.getChildNodes();
            Map<String, String> result3 = new HashMap<>();
            //子节点遍历
            for (int i = 0; i < items.getLength(); i++) {
                Node item = items.item(i);
                String iName = item.getNodeName();
//                System.out.println(iName);
                //<ToUserName><![CDATA[gh_b6a171776f25]]></ToUserName>
                //注意：ToUserName标签内部的文本内容实际上也是一个节点，这里不能通过getNodeValue直接获取节点内容
                String value = item.getTextContent();
                if (iName.equals("#text")) {
                    continue;
                }
                result.put(iName, value);
            }


            //遍历result2
            String toUser = result.get("FromUserName");

            System.out.println(toUser);
            if(encrypt == null || encrypt.equals("raw")){

                //xml格式化
                String xml = "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[%s]]></MsgType>" +
                        "<Content><![CDATA[%s]]></Content>" +
                        "</xml>";
                String fromUser = result.get("ToUserName");
                String createTime = System.currentTimeMillis() / 1000 + "";
                String msgType = "text";
                String content = "hello";
                //格式化输出
                String res =String.format(xml, toUser, fromUser, createTime, msgType, content);
                //response相应输出
                response.getWriter().println(res);
            }
            else{

            };
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (AesException e) {
            e.printStackTrace();
        }
    }


    //get请求处理
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //微信公众号管理界面配置参数
        String token = Const.Token;
        //获取请求的四个参数
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        //检验四个参数是否有效
        if (!StringUtil.hasBlank(signature, timestamp, nonce, echostr)) {
            String[] list = {token, timestamp, nonce};
            //字典排序
            Arrays.sort(list);
            //拼接字符串
            StringBuilder builder = new StringBuilder();
            for (String str : list) {
                builder.append(str);
            }
            //sha1加密
            String hashcode = EncryptUtil.sha1(builder.toString());
            //不区分大小写差异情况下比较是否相同
            if (hashcode.equalsIgnoreCase(signature)) {
                //响应输出
                response.getWriter().println(echostr);
            }
        }
    }
}
