package Servlet;

import Service.StuListService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "StuListServlet", value = "/stulist")
public class StuListServlet extends HttpServlet {
    //post请求处理
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        PrintWriter news=new PrintWriter(response.getOutputStream());
//        System.out.println(request.getParameter("username"));

        StuListService stulistService = new StuListService();
        stulistService.stulist(request);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

}