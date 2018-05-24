package ru.innopolis.stc9.controllers.filter;


import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserFilter implements Filter {
    private static Logger logger = Logger.getLogger(UserFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //some comment
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        if ((request == null) || (response == null)) return;
        HttpSession httpSession = ((HttpServletRequest) request).getSession();
        try {
            if (httpSession.getAttribute("login") != null) {
                chain.doFilter(request, response);
            } else {
                HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login?errorMsg=noAuth");
            }
        } catch (ServletException e) {
            logger.error("ServletException: " + e.getMessage());
        } catch (IOException e) {
            logger.error("IOException: " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        //some comment
    }
}
