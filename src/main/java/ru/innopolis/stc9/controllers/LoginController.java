package ru.innopolis.stc9.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.stc9.service.UserService;
import ru.innopolis.stc9.service.UserServiceImpl;

@Controller
public class LoginController {
    private final Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    private final UserService userService = new UserServiceImpl();

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", "invalid username or password");
        }
        if (logout != null) {
            model.addAttribute("msg", "logged out successfully");
        }
        return "login";
    }

/*    @RequestMapping(value = "/login", method = RequestMethod.GET)
    private String loginForm(@RequestParam(value = "action", required = false) String action,
                             HttpSession session,
                             Model model) {
        if ("logout".equals(action)) {
            logger.info("logout: " + session.getAttribute("login"));
            session.invalidate();
        }
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    private String postLoginForm(HttpServletRequest req,
                                 HttpServletResponse resp,
                                 Model model) {
        String login = req.getParameter("userName");
        String password = req.getParameter("userPassword");
        String redirectPath = req.getContextPath();
        if (userService.checkAuth(login, password)) {
            Integer role = userService.getRole(login);
            logger.info("login: " + login + ", role: " + role);
            req.getSession().setAttribute("login", login);
            req.getSession().setAttribute("role", role);
            redirectPath += "/views/progress";
        } else {
            redirectPath += "/login?errorMsg=authError";
        }
        try {
            resp.sendRedirect(redirectPath);
        } catch (IOException e) {
            logger.error("IOException: " + e.getMessage());
        }
        return "login";
    }*/
}
