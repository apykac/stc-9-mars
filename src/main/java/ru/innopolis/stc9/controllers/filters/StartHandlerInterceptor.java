package ru.innopolis.stc9.controllers.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import ru.innopolis.stc9.controllers.SessionDataInform;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.interfaces.MessageService;
import ru.innopolis.stc9.service.interfaces.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class StartHandlerInterceptor implements HandlerInterceptor {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute(SessionDataInform.ID) == null) {
            User user = userService.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
            int numberOfMessage = messageService.getNumberOfMessage(user);
            addStartAttributeToSession(session, user, numberOfMessage);
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
