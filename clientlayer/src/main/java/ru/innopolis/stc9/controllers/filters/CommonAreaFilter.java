package ru.innopolis.stc9.controllers.filters;

import org.apache.log4j.Logger;
import ru.innopolis.stc9.controllers.SessionDataInform;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({"/university/*", "/temp/*"})
public class CommonAreaFilter implements Filter {
    private static Logger logger = Logger.getLogger(CommonAreaFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        //some comment
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        try {
            if (session.getAttribute(SessionDataInform.ROLE) == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
            } else chain.doFilter(request, response);
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
