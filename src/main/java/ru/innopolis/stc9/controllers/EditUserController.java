package ru.innopolis.stc9.controllers;


import org.apache.log4j.Logger;
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
            req.getSession().setAttribute("user-id", id);
        }
        if (adminService.getLoginUpdateMessage(req) != null) {
            req.setAttribute("loginUpdateMessage", adminService.getLoginUpdateMessage(req));
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
    }
}
