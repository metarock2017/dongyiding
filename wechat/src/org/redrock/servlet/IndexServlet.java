package org.redrock.servlet;

import org.apache.commons.lang3.StringUtils;
import org.redrock.service.CreateroomService;
import org.redrock.service.JoinroomService;
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
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


//配置路由
@WebServlet(name = "IndexServlet", value = "/index")
public class IndexServlet extends HttpServlet {


    //post请求处理
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
//            Map<String, String[]> params = request.getParameterMap();
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

            String Content = result.get("Content");
            String Event = result.get("Event");
            String EventKey = result.get("EventKey");
            //System.out.println(result);

            //关注事件
            if (Event != null && Event.equals("subscribe")) {
                //xml格式化
                String xml = "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[%s]]></MsgType>" +
                        "<ArticleCount>1</ArticleCount>" +
                        "<Articles>" +
                        "<item>" +
                        "<Title><![CDATA[%s]]></Title> " +
                        "<Description><![CDATA[%s]]></Description>" +
                        "<PicUrl><![CDATA[%s]]></PicUrl>" +
                        "<Url><![CDATA[%s]]></Url>" +
                        "</item>" +
                        "</Articles>" +
                        "</xml>";
                String toUser = result.get("FromUserName");
                String fromUser = result.get("ToUserName");
                String createTime = System.currentTimeMillis() / 1000 + "";
                String msgType = "news";
                String Title = "Hello";
                String Description = "欢迎关注dyd的公众号";
                String PicUrl = "http://b114.photo.store.qq.com/psb?/V12Dh5WD3OfwB5/9LyRsHTnwqB1*6gWEVcbTfqEuTvqLKoXlh6kFDQGAeo!/b/dHIAAAAAAAAA&bo=xgCaAAAAAAAREHs!&rf=viewer_311";
                String Url = "https://github.com/metarock2017/dongyiding";
                //格式化输出
                String res = String.format(xml, toUser, fromUser, createTime, msgType, Title, Description, PicUrl, Url);
                //response相应输出
                response.setCharacterEncoding("UTF-8");
                response.getWriter().println(res);
            }

            //被动回复
            else if (Content != null) {
                switch (Content) {
                    case "redrock":
                        String xml = "<xml>" +
                                "<ToUserName><![CDATA[%s]]></ToUserName>" +
                                "<FromUserName><![CDATA[%s]]></FromUserName>" +
                                "<CreateTime>%s</CreateTime>" +
                                "<MsgType><![CDATA[%s]]></MsgType>" +
                                "<ArticleCount>1</ArticleCount>" +
                                "<Articles>" +
                                "<item>" +
                                "<Title><![CDATA[%s]]></Title> " +
                                "<Description><![CDATA[%s]]></Description>" +
                                "<PicUrl><![CDATA[%s]]></PicUrl>" +
                                "<Url><![CDATA[%s]]></Url>" +
                                "</item>" +
                                "</Articles>" +
                                "</xml>";
                        String toUser = result.get("FromUserName");
                        String fromUser = result.get("ToUserName");
                        String createTime = System.currentTimeMillis() / 1000 + "";
                        String msgType = "news";
                        String Title = "Redrock";
                        String Description = "这是重庆邮电大学最牛逼的学生组织没有之一";
                        String PicUrl = "http://hongyan.cqupt.edu.cn/images/index_top.jpg";
                        String Url = "http://hongyan.cqupt.edu.cn/";
                        //格式化输出
                        String res = String.format(xml, toUser, fromUser, createTime, msgType, Title, Description, PicUrl, Url);
                        //response相应输出
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().println(res);
                        break;
                    default:
                        int roomID = 0;
                        toUser = result.get("FromUserName");
                        fromUser = result.get("ToUserName");
                        Content = result.get("Content");
                        String resultMessage = "";
                        if (StringUtils.isNumeric(Content)) {
                            roomID = Integer.parseInt(Content);
                            if (roomID >= 4 && roomID <= 13) {
                                CreateroomService createroomService = new CreateroomService();
                                JoinroomService joinroomService = new JoinroomService();
                                resultMessage = joinroomService.join(toUser, createroomService.create(roomID, toUser), 0);
                            } else if (roomID > 1000 && roomID < 10000) {
                                JoinroomService joinroomService = new JoinroomService();
                                resultMessage = joinroomService.join(toUser, roomID);
                            }
//                            else if (Content!=null && Content.equals("换词")) {
//                                ChangewordsService changewordsService = new ChangewordsService();
//                                resultMessage = changewordsService.change(toUser);
//                            }
                            else {
                                resultMessage = "点击“谁是卧底”创建房间\n回复房间号加入房间";
                            }
                        }else {
                            resultMessage = "点击“谁是卧底”创建房间\n回复房间号加入房间";
                        }
                        xml = "<xml>" +
                                "<ToUserName><![CDATA[%s]]></ToUserName>" +
                                "<FromUserName><![CDATA[%s]]></FromUserName>" +
                                "<CreateTime>%s</CreateTime>" +
                                "<MsgType><![CDATA[%s]]></MsgType>" +
                                "<Content><![CDATA[%s]]></Content>" +
                                "</xml>";
                        createTime = System.currentTimeMillis() / 1000 + "";
                        msgType = "text";
                        //格式化输出
                        res = String.format(xml, toUser, fromUser, createTime, msgType, resultMessage);
                        //response相应输出
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().println(res);
                        break;
                }

            }

            //菜单
            else if (Event != null && Event.equals("CLICK") && EventKey.equals("createroom")) {
                CreateroomService createroomService = new CreateroomService();
                String toUser = result.get("FromUserName");
                String fromUser = result.get("ToUserName");
                String xml = "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[%s]]></MsgType>" +
                        "<Content><![CDATA[%s]]></Content>" +
                        "</xml>";
                String createTime = System.currentTimeMillis() / 1000 + "";
                String msgType = "text";
                String content = "创建成功，请回复4-13中的数字设置房间人数";
                //格式化输出
                String res = String.format(xml, toUser, fromUser, createTime, msgType, content);
                //response相应输出
                response.setCharacterEncoding("UTF-8");
                response.getWriter().println(res);
            }


            //加入房间 回复消息
            else if (Content != null && Integer.parseInt(Content) >= 1000 && Integer.parseInt(Content) <= 9999) {
                String toUser = result.get("FromUserName");
                String fromUser = result.get("ToUserName");
                int roomId = Integer.parseInt(result.get("Content"));
                JoinroomService joinroomService = new JoinroomService();
                String xml = "<xml>" +
                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
                        "<CreateTime>%s</CreateTime>" +
                        "<MsgType><![CDATA[%s]]></MsgType>" +
                        "<Content><![CDATA[%s]]></Content>" +
                        "</xml>";
                String createTime = System.currentTimeMillis() / 1000 + "";
                String msgType = "text";
                String content = joinroomService.join(toUser, roomId);
                //格式化输出
                String res = String.format(xml, toUser, fromUser, createTime, msgType, content);
                //response相应输出
                response.setCharacterEncoding("UTF-8");
                response.getWriter().println(res);

            }
//            else if (Content!=null && Content.equals("换词")) {
//                String toUser = result.get("FromUserName");
//                String fromUser = result.get("ToUserName");
//                ChangewordsService changewordsService = new ChangewordsService();
//                String xml = "<xml>" +
//                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
//                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
//                        "<CreateTime>%s</CreateTime>" +
//                        "<MsgType><![CDATA[%s]]></MsgType>" +
//                        "<Content><![CDATA[%s]]></Content>" +
//                        "</xml>";
//                String createTime = System.currentTimeMillis() / 1000 + "";
//                String msgType = "text";
//                String content = changewordsService.change(toUser);
//                //格式化输出
//                String res = String.format(xml, toUser, fromUser, createTime, msgType, content);
//                //response相应输出
//                response.setCharacterEncoding("UTF-8");
//                response.getWriter().println(res);
//            }


            else {

            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //校验
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
