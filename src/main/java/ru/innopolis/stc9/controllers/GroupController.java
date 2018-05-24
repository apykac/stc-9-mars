package ru.innopolis.stc9.controllers;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.pojo.Group;
import ru.innopolis.stc9.service.GroupService;
import ru.innopolis.stc9.service.GroupServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Сергей on 23.05.2018.
 * Выводит список всех групп и форму создания новой группы
 */
@WebServlet("/views/allgroup")
public class GroupController extends HttpServlet {
    private final Logger logger = Logger.getLogger(GroupController.class);
    private static final String ENCODING = "UTF-8";
    private final GroupService service = new GroupServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        if ((req == null) || (resp == null)) return;
        try {
            req.setCharacterEncoding(ENCODING);
            resp.setCharacterEncoding(ENCODING);
            req.setAttribute("groups", service.findAllGroups());
            req.getRequestDispatcher(req.getContextPath() + "/views/allGroups.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("in forward - exception" + e.getMessage());
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
        Group group = new Group(req.getParameter("name"));
        service.addGroup(group);
        logger.info("group added");
        req.setAttribute("groups", service.findAllGroups());
        try {
            req.getRequestDispatcher(req.getContextPath() + "allGroups.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error("in forward - exception" + e.getMessage());
        }
    }
}
