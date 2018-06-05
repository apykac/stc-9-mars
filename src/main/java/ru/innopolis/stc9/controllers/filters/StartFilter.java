package ru.innopolis.stc9.controllers.filters;

import org.apache.log4j.Logger;


public class StartFilter {
    private static Logger logger = Logger.getLogger(StartFilter.class);

    /*@Autowired
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) {
        ApplicationContext context = new ClassPathXmlApplicationContext("MainController-servlet.xml");
        userService = (UserService) context.getBean("userServiceImpl");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        try {
            if (session.getAttribute(SessionDataInform.ID) == null) {
                User user = userService.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
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
    }*/
}
