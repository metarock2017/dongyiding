package Servlet;

import Service.AddStuService;
import Service.GetStuService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "GetStuServlet", value = "/getstu")
public class GetStuServlet extends HttpServlet {
    //post请求处理
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GetStuService getStuService = new GetStuService();
        try {
            getStuService.getstu(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

}