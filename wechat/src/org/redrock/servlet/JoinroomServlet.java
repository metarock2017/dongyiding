package org.redrock.servlet;

import org.redrock.service.JoinroomService;
import org.redrock.servlet.handle.ServletDataHandle;
import org.redrock.util.Const;
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
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "Joinroom", value = "/joinroom")
public class JoinroomServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
