package ru.innopolis.stc9.controllers.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession httpSession = ((HttpServletRequest) request).getSession();
        if (httpSession.getAttribute("login") != null) {
            chain.doFilter(request, response);
        } else {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login?errorMsg=noAuth");
        }
    }

    @Override
    public void destroy() {

    }
}
