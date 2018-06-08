package ru.innopolis.stc9.controllers.filters;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.innopolis.stc9.controllers.SessionDataInform;
import ru.innopolis.stc9.dao.implementation.UserDaoImpl;
import ru.innopolis.stc9.dao.interfaces.UserDao;
import ru.innopolis.stc9.pojo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/start")
public class StartFilter implements Filter {
    private static Logger logger = Logger.getLogger(StartFilter.class);
    private UserDao userDao = new UserDaoImpl();

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        try {
            if (session.getAttribute(SessionDataInform.ID) == null) {
                User user = userDao.findLoginByName(SecurityContextHolder.getContext().getAuthentication().getName());
                session.setAttribute(SessionDataInform.ID, user.getId());
                session.setAttribute(SessionDataInform.LOGIN, user.getLogin());
                session.setAttribute(SessionDataInform.NAME, user.getFirstName() + " " + user.getSecondName());
                session.setAttribute(SessionDataInform.ROLE, user.getPermissionGroup());
                logger.info("User: [" + user.getId() + "] " + user.getLogin() + " is login");
                resp.sendRedirect(req.getContextPath() + "/university/start");
            }
            chain.doFilter(request, response);
        } catch (IOException e) {
            logger.error("IOException: " + e.getMessage());
        } catch (ServletException e) {
            logger.error("ServletException: " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        //some comment
    }
}
