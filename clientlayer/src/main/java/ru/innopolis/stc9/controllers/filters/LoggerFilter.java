package ru.innopolis.stc9.controllers.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class LoggerFilter implements Filter {
    private static Logger logger = Logger.getLogger(LoggerFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        //some comment
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest req = (HttpServletRequest) request;
        logger.info("Servlet Path: " + req.getServletPath() + " @ URL: " + req.getRequestURL());
        try {
            chain.doFilter(request, response);
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
