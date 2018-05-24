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
 * Обновляет название группы
 */
@WebServlet("/views/group")
public class UpdateGroupController extends HttpServlet {
    private final Logger logger = Logger.getLogger(GroupController.class);
    private static final String ENCODING = "UTF-8";
    private final GroupService service = new GroupServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        if ((req == null) || (resp == null)) return;
        try {
            req.setCharacterEncoding(ENCODING);
            resp.setCharacterEncoding(ENCODING);
            int groupId = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("groupName", service.findGroupById(groupId).getName());
            req.setAttribute("id", groupId);
            logger.info("group for update");
            req.getRequestDispatcher(req.getContextPath() + "/views/group.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            logger.error("NumberFormatException: " + e.getMessage());
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
            Group group = service.findGroupById(Integer.parseInt(req.getParameter("id")));
            group.setName(req.getParameter("name"));
            service.updateGroup(group);
            resp.sendRedirect(req.getContextPath() + "/views/allgroup");
        } catch (NumberFormatException e) {
            logger.error("NumberFormatException: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException: " + e.getMessage());
        } catch (IOException e) {
            logger.error("IOException: " + e.getMessage());
        }
    }
}
