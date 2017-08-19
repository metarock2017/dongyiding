package Servlet;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "XmlServlet", value = "/xml")
public class XmlServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "  <soap:Body>\n" +
                    "    <GetUserPostTypes xmlns=\"http://172.20.2.52:84/\" />\n" +
                    "  </soap:Body>\n" +
                    "</soap:Envelope>";
            String url = "http://172.20.2.52:84/userpostservice.asmx";
            InputStream test = test(url, xml);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(test);
            //<xml>
            //  <ToUserName><![CDATA[gh_b6a171776f25]]></ToUserName>
            //  <FromUserName><![CDATA[oiL6j0WJxy7Nagpnt6rX7Yo_5LeM]]></FromUserName><CreateTime>1501741106</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[emmm]]></Content><MsgId>6449928937743195428</MsgId></xml>
            //获取根节点
            Element rootNode = document.getDocumentElement();
            //根节点名
            String name = rootNode.getNodeName();
            //获取子节点数组
            NodeList items = rootNode.getChildNodes();
            Map<String, String> result = new HashMap<>();
            //子节点遍历
            for (int i = 0; i < items.getLength(); i++) {
                Node item = items.item(i);
                String iName = item.getNodeName();
                //<ToUserName><![CDATA[gh_b6a171776f25]]></ToUserName>
                //注意：ToUserName标签内部的文本内容实际上也是一个节点，这里不能通过getNodeValue直接获取节点内容
                String value = item.getTextContent();
                if (iName.equals("#text")) {
                    continue;
                }
                result.put(iName, value);
            }
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public InputStream test(String strUrl,String data){
        String result="";
        InputStream inputStream=null;
        try{
        URL url = new URL(strUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("Content-Type","application/soap+xml; charset=utf-8");
        connection.setRequestProperty("SOAPAction","http://172.20.2.52:84/GetUserPostTypes");
        connection.setRequestProperty("Host","172.20.2.52");
        connection.setConnectTimeout(3000);
        connection.connect();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        connection.getOutputStream(), "UTF-8"
                )
        );
        writer.write(data);
        writer.flush();
        int code=connection.getResponseCode();


        if(code==connection.HTTP_INTERNAL_ERROR)
            inputStream=connection.getErrorStream();
    } catch (MalformedURLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
        return inputStream;
}
    }
