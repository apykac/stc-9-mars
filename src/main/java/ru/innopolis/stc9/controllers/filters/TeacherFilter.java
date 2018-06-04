package ru.innopolis.stc9.controllers.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/university/teacher/*")
public class TeacherFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
        //some comment
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        if (!"ROLE_ADMIN".equals(session.getAttribute("entered_role")) ||
                "ROLE_TEACHER".equals(session.getAttribute("entered_role")))
            resp.sendRedirect(req.getContextPath() + "/start");
        else chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        //some comment
    }
}
