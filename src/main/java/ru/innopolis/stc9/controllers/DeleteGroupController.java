package ru.innopolis.stc9.controllers;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.service.GroupService;
import ru.innopolis.stc9.service.GroupServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        if ((req == null) || (resp == null)) return;
        try {
            req.setCharacterEncoding(ENCODING);
            resp.setCharacterEncoding(ENCODING);
            int id = Integer.parseInt(req.getParameter("id"));
            service.deleteGroup(id);
            logger.info("group deleted");
            resp.sendRedirect(req.getContextPath() + "/views/allgroup");
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException: " + e.getMessage());
        } catch (NumberFormatException e) {
            logger.error("NumberFormatException: " + e.getMessage());
        } catch (IOException e) {
            logger.error("IOException: " + e.getMessage());
        }
    }
}
