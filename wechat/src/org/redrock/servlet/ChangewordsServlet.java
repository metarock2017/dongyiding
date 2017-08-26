//package org.redrock.servlet;
//
//import org.redrock.service.ChangewordsService;
//import org.redrock.service.CreateroomService;
//import org.redrock.servlet.handle.ServletDataHandle;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebServlet(name = "Changewords", value = "/changewords")
//public class ChangewordsServlet extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        ServletDataHandle servletDataHandle = new ServletDataHandle();
//        ChangewordsService changewordsService = new ChangewordsService();
//
//        String json = null;
//        //json = createroomService.create(10, Const.AppId);
//        System.out.println(11111111);
//        //resp.getWriter().print(json);
//    }
//}
