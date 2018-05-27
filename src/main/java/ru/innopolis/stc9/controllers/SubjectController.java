package ru.innopolis.stc9.controllers;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.service.SubjectService;
import ru.innopolis.stc9.service.SubjectServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SubjectController extends HttpServlet {
    private final Logger logger = Logger.getLogger(GroupController.class);
    private final SubjectService subjectService = new SubjectServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("subjects", subjectService.findAllSubject());
        try {
            req.getRequestDispatcher(req.getContextPath() + "/views/subject.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            logger.error(e.getMessage());
        }

    }
}