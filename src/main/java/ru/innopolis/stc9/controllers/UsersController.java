package ru.innopolis.stc9.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.interfaces.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UsersController {
    private UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/admin/edit_user/{id}/delete", method = RequestMethod.POST)
    public String delUserPost(@PathVariable("id") int id, HttpSession session, Model model) {
        if (userService.delUserById(id)) return getUserListGet(model);
        else return editUserGet(id, session, false, model);
    }

    @RequestMapping(value = "/admin/users_list", method = RequestMethod.GET)
    public String getUserListGet(Model model) {
        model.addAttribute("usersList", userService.getUserList());
        return "/views/allUsers";
    }

    @RequestMapping(value = "/admin/edit_user/{id}", method = RequestMethod.GET)
    public String editUserGet(@PathVariable("id") int id,
                              HttpSession session,
                              boolean isOwner,
                              Model model) {
        if (id > 0) {
            User user = userService.findUserByIdWithSubjectList(id);
            if (!isOwner && user.getEnabled() != 0 &&
                    ((id == (Integer) session.getAttribute(SessionDataInform.ID))
                            || (user.getPermissionGroup().equals("ROLE_ADMIN"))))
                return "redirect:/start";
            model.addAttribute("user", user);
            model.addAttribute("isOwner", isOwner);
        }
        return "/views/editUser";
    }

    @RequestMapping(value = "/temp/edit_user", method = RequestMethod.GET)
    public String editUserGet() {
        return "redirect:/start";
    }

    @RequestMapping(value = "/temp/edit_user", method = RequestMethod.POST)
    public String editUserPost(@RequestBody MultiValueMap<String, String> incParam, HttpSession session, Model model) {
        Object[] info = userService.editUser(incParam);
        if (((List) info[0]).isEmpty()) info[0] = null;
        if (((List) info[1]).isEmpty()) info[1] = null;
        if ((Boolean) info[2]) updateSession(session);
        model.addAttribute("errors", info[0]);
        model.addAttribute("success_list", info[1]);
        return editUserGet(Integer.parseInt(incParam.get("id").get(0)),
                session,
                Boolean.parseBoolean(incParam.get("isOwner").get(0)),
                model);
    }

    @RequestMapping(value = "/university/profile", method = RequestMethod.GET)
    public String editOwnerProfile(HttpSession session, Model model) {
        return editUserGet((Integer) session.getAttribute(SessionDataInform.ID),
                session,
                true,
                model);
    }

    @RequestMapping(value = "/university/profile/delete", method = RequestMethod.GET)
    public String deleteAccountGet() {
        return "/views/deleteAccountPage";
    }

    @RequestMapping(value = "/university/profile/delete", method = RequestMethod.POST)
    public String deleteAccountPost(@RequestParam(value = "password") String candidate, HttpSession session, Model model) {
        int id = (Integer) session.getAttribute(SessionDataInform.ID);
        if (userService.checkPasswordOfCurrentAccount(id, candidate))
            if (userService.deactivationCurrentAccount(id))
                return new LoginController().logout(session);
            else model.addAttribute("error", "Error deactivation by DAO");
        else model.addAttribute("error", "Incorrect password");
        return deleteAccountGet();
    }

    private void updateSession(HttpSession session) {
        User user = userService.findUserById((Integer) session.getAttribute(SessionDataInform.ID));
        session.setAttribute(SessionDataInform.LOGIN, user.getLogin());
        session.setAttribute(SessionDataInform.NAME, user.getFirstName() + " " + user.getSecondName());
        session.setAttribute(SessionDataInform.ROLE, user.getPermissionGroup());
    }
}
