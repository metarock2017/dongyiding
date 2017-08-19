package Servlet;

import Service.AddStuService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddStuServlet", value = "/addstu")
public class AddStuServlet extends HttpServlet {
    //post请求处理
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println(request.getParameter("username"));
//        PrintWriter news=new PrintWriter(response.getOutputStream());
//        System.out.println(request.getParameter("username"));
        request.setCharacterEncoding("utf-8");
        AddStuService addStuService = new AddStuService();
        addStuService.addstu(request);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

}