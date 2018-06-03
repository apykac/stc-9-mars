package ru.innopolis.stc9.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UsersController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin/users_list", method = RequestMethod.GET)
    public String getUserListGet(Model model) {
        model.addAttribute("usersList", userService.getUserList());
        return "/views/allUsers";
    }

    @RequestMapping(value = "/admin/edit_user", method = RequestMethod.GET)
    public String editUserGet(@RequestParam(value = "selected_user_id", required = false) int id,
                              HttpSession session,
                              boolean isOwner,
                              Model model) {
        if (id > 0) {
            User user = userService.findUserById(id);
            if (!isOwner &&
                    ((id == (Integer) session.getAttribute("entered_user_id"))
                            || user.getPermissionGroup().equals("ROLE_ADMIN")))
                return "redirect:/start";
            model.addAttribute("user", user);
            model.addAttribute("isOwner", isOwner);
        }
        return "/views/editUser";
    }

    @RequestMapping(value = "/temp/edit_user", method = RequestMethod.POST)
    public String editUserPost(@RequestBody MultiValueMap<String, String> incParam, HttpSession session, Model model) {
        Object[] info = userService.editUser(incParam);
        if (((List) info[0]).isEmpty()) info[0] = null;
        model.addAttribute("errors", info[0]);
        if (((List) info[1]).isEmpty()) info[1] = null;
        model.addAttribute("success_list", info[1]);
        return editUserGet(Integer.parseInt(incParam.get("id").get(0)),
                session,
                Boolean.parseBoolean(incParam.get("isOwner").get(0)),
                model);
    }

    @RequestMapping(
            value = {"/university/student/profile", "/university/teacher/profile", "admin/profile"},
            method = RequestMethod.GET)
    public String editOwnerProfile(HttpSession session, Model model) {
        return editUserGet((Integer) session.getAttribute("entered_user_id"),
                session,
                true,
                model);
    }
}
