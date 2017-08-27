package org.redrock.servlet;

import org.redrock.service.CreateroomService;
import org.redrock.servlet.handle.ServletDataHandle;
import org.redrock.util.Const;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Createroom", value = "/createroom")
public class CreateroomServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletDataHandle servletDataHandle = new ServletDataHandle();
        CreateroomService createroomService = new CreateroomService();

        String json = null;
    }
}
