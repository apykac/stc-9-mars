package ru.innopolis.stc9.controllers.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import ru.innopolis.stc9.controllers.SessionDataInform;
import ru.innopolis.stc9.dao.interfaces.MessageDao;
import ru.innopolis.stc9.dao.interfaces.UserDao;
import ru.innopolis.stc9.pojo.Message;
import ru.innopolis.stc9.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class StartHandlerInterceptor implements HandlerInterceptor {
    @Autowired
    private UserDao userDao;
    @Autowired
    private MessageDao messageDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute(SessionDataInform.ID) == null) {
            User user = userDao.findLoginByName(SecurityContextHolder.getContext().getAuthentication().getName());
            List<Message> countOfMessage = messageDao.getAllMessagesByRole(user.getPermissionGroup());
            addStartAttributeToSession(session, user, countOfMessage.size());
            response.sendRedirect(request.getContextPath() + "/university/start");
        }
        return true;
    }

    private void addStartAttributeToSession(HttpSession session, User user, int count) {
        session.setAttribute(SessionDataInform.ID, user.getId());
        session.setAttribute(SessionDataInform.LOGIN, user.getLogin());
        session.setAttribute(SessionDataInform.NAME, user.getFirstName() + " " + user.getSecondName());
        session.setAttribute(SessionDataInform.ROLE, user.getPermissionGroup());
        session.setAttribute(SessionDataInform.MSG, count);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView
            modelAndView) {
        //some comment
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception
            ex) {
        //some comment
    }
}
