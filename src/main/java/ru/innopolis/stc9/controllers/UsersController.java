package ru.innopolis.stc9.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.stc9.service.AdminService;

import java.util.List;

@Controller
public class UsersController {
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/admin/edit-user", method = RequestMethod.GET)
    public String editUserGet(@RequestParam(value = "user-id", required = false) int id,
                              @RequestParam(value = "info", required = false) String info,
                              Model model) {
        if (id > 0) model.addAttribute("user", adminService.getUser(id));
        model.addAttribute("info", info);
        return "/views/editUser";
    }

    @RequestMapping(value = "/admin/edit-user", method = RequestMethod.POST)
    public String editUserPost(@RequestBody MultiValueMap<String, String> incParam, Model model) {
        List<String> errors = adminService.editUser(incParam);
        if (errors.isEmpty()) errors = null;
        model.addAttribute("errors", errors);
        return "/views/editUser";
    }

    @RequestMapping(value = "/admin/users-list", method = RequestMethod.GET)
    public String getUserListGet(Model model) {
        model.addAttribute("usersList", adminService.getUsersList());
        return "/views/allUsers";
    }
}
