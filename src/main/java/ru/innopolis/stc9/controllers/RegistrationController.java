package ru.innopolis.stc9.controllers;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.service.LoginService;
import ru.innopolis.stc9.service.LoginServiceImpl;
import ru.innopolis.stc9.service.UserService;
import ru.innopolis.stc9.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegistrationController extends HttpServlet {
    private static final String ENCODING = "UTF-8";
    private static Logger logger = Logger.getLogger(RegistrationController.class);
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        if ((req == null) || (resp == null)) return;
        try {
            req.setCharacterEncoding(ENCODING);
            resp.setCharacterEncoding(ENCODING);
            req.getRequestDispatcher(req.getContextPath() + "/registration.jsp").forward(req, resp);
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException: " + e.getMessage());
        } catch (ServletException e) {
            logger.error("ServletException: " + e.getMessage());
        } catch (IOException e) {
            logger.error("IOException: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        if ((req == null) || (resp == null)) return;
        try {
            req.setCharacterEncoding(ENCODING);
            resp.setCharacterEncoding(ENCODING);
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException: " + e.getMessage());
        }
        Map<String, String[]> incParam = req.getParameterMap();
        List<String> errorList = new ArrayList<>();
        if (userService.isExist(incParam.get("login")[0])) errorList.add("Login is Exist");
        else errorList = userService.isCorrectData(incParam);
        if (!errorList.isEmpty()) {
            req.setAttribute("errorMsg", errorList);
            forwardErrors(req, resp);
            return;
        }
        userService.addUserByParam(incParam);
        try {
            resp.sendRedirect(req.getContextPath() + "/login?registration=true");
        } catch (IOException e) {
            logger.error("IOException: " + e.getMessage());
        }
    }

    private void forwardErrors(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/registration.jsp?error=true").forward(req, resp);
        } catch (ServletException e) {
            logger.error("ServletException: " + e.getMessage());
        } catch (IOException e) {
            logger.error("IOException: " + e.getMessage());
        }
    }
}
