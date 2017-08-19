package Servlet;

import Service.DelStuService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DelStuServlet", value = "/delstu")
public class DelStuServlet extends HttpServlet {
    //post请求处理
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DelStuService delStuService = new DelStuService();
        try {
            delStuService.delstu(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

}
