package ru.innopolis.stc9.controllers;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.innopolis.stc9.service.AdminService;
import ru.innopolis.stc9.service.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/views/edit-user")
public class EditUserController extends HttpServlet{
    private final Logger logger = Logger.getLogger(GroupController.class);
    private static final String ENCODING = "UTF-8";
    //@Autowired
    private final AdminService adminService = new AdminServiceImpl();

    /*@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("user-id"));
        if (id>0) {
            req.setAttribute("user", adminService.getUser(id));
            req.getSession().setAttribute("user-id", id);
        }
        if (adminService.getUserUpdateMessage(req) != null) {
            req.setAttribute("userUpdateMessage", adminService.getUserUpdateMessage(req));
        }
        if (adminService.getPasswordUpdateMessage(req) != null && !adminService.getPasswordUpdateMessage(req).isEmpty()) {
            req.setAttribute("passwordUpdateMessage", adminService.getPasswordUpdateMessage(req));
        }
        req.getRequestDispatcher(req.getContextPath() + "/views/editUser.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg = adminService.editUser(req);
        resp.sendRedirect(req.getContextPath() + msg);
    }*/
}
