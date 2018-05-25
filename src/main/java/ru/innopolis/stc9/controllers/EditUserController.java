package ru.innopolis.stc9.controllers;


import org.apache.log4j.Logger;
import ru.innopolis.stc9.pojo.Login;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.AdminService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/views/edit-user")
public class EditUserController extends HttpServlet{
    private final Logger logger = Logger.getLogger(GroupController.class);
    private static final String ENCODING = "UTF-8";
    private final AdminService adminService = new AdminService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("user-id"));
        if (id>0) {
            req.setAttribute("user", adminService.getUser(id));
            req.setAttribute("login", adminService.getLogin(id));
        }
        req.getRequestDispatcher(req.getContextPath() + "/views/editUser.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
