package Servlet;

import Service.ChangeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ChangeServlet", value = "/change")
public class ChangeServlet extends HttpServlet {
    //post请求处理
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getParameter("username"));
        PrintWriter news=new PrintWriter(response.getOutputStream());
//        System.out.println(request.getParameter("username"));
        ChangeService changeService = new ChangeService();
        changeService.change(request);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

}