package ru.innopolis.stc9.controllers;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.service.AdminService;
import ru.innopolis.stc9.service.GroupService;
import ru.innopolis.stc9.service.GroupServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/views/users-list")
public class UsersListController extends HttpServlet {
    private final Logger logger = Logger.getLogger(GroupController.class);
    private static final String ENCODING = "UTF-8";
    private final AdminService adminService = new AdminService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(ENCODING);
        resp.setCharacterEncoding(ENCODING);
        req.setAttribute("usersList", adminService.getUsersList());
        try {
            req.getRequestDispatcher(req.getContextPath() + "/views/allUsers.jsp").forward(req,resp);
        } catch (ServletException | IOException e) {
            logger.error(e.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
