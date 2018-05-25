package ru.innopolis.stc9.controllers;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.service.LoginService;
import ru.innopolis.stc9.service.LoginServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginController extends HttpServlet {
    private final Logger logger = Logger.getLogger(LoginController.class);
    private final LoginService loginService = new LoginServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        if ((req == null) || (resp == null)) return;
        String action = req.getParameter("action");
        if ("logout".equals(action)) {
            logger.info("logout: " + req.getSession().getAttribute("login"));
            req.getSession().invalidate();
        }
        try {
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("in forward - exception" + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        if ((req == null) || (resp == null)) return;
        String login = req.getParameter("userName");
        String password = req.getParameter("userPassword");
        String redirectPath;
        if (loginService.checkAuth(login, password)) {
            Integer role = loginService.getRole(login);
            logger.info("login: " + login + ", role: " + role);
            req.getSession().setAttribute("login", login);
            req.getSession().setAttribute("role", role);
            redirectPath = req.getContextPath() + "/views";
        } else {
            redirectPath = req.getContextPath() + "/login?errorMsg=authError";
        }
        try {
            resp.sendRedirect(redirectPath);
        } catch (IOException e) {
            logger.error("IOException: " + e.getMessage());
        }
    }
}
