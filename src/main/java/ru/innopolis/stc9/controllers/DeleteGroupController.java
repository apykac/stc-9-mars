package ru.innopolis.stc9.controllers;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.service.GroupService;
import ru.innopolis.stc9.service.GroupServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Сергей on 23.05.2018.
 * Удаляет группу
 */
@WebServlet("/views/deleteGroup")
public class DeleteGroupController extends HttpServlet {
    private final Logger logger = Logger.getLogger(GroupController.class);
    private static final String ENCODING = "UTF-8";
    private final GroupService service = new GroupServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(ENCODING);
        resp.setCharacterEncoding(ENCODING);
        service.deleteGroup(Integer.parseInt(req.getParameter("id")));
        logger.info("group deleted");
        resp.sendRedirect(req.getContextPath() + "/views/allgroup");
    }
}
