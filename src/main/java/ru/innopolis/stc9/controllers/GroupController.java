package ru.innopolis.stc9.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.stc9.pojo.Group;
import ru.innopolis.stc9.service.GroupService;
import ru.innopolis.stc9.service.UserService;


/**
 * Created by Сергей on 23.05.2018.
 * Выводит список всех групп и форму создания новой группы
 */
@Controller
public class GroupController {
    private final Logger logger = Logger.getLogger(GroupController.class);
    private static final String ENCODING = "UTF-8";
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;

    @RequestMapping("/views/allgroup")
    public String viewAllGroups(Model model) {
        model.addAttribute("groups", groupService.findAllGroups());
        return "views/allGroups";
    }


    @RequestMapping("/views/addgroups")
    public String addGroup(@RequestParam("name") String name, Model model) {
        Group group = new Group(name);
        groupService.addGroup(group);
        logger.info("group added");
        model.addAttribute("groups", groupService.findAllGroups());
        return "views/allGroups";
    }

    @RequestMapping("/views/group/{id}")
    public String forUpdateGroup(@PathVariable("id") int id, Model model) {
        model.addAttribute("groupName", groupService.findGroupById(id).getName());
        model.addAttribute("id", id);
        logger.info("group for update");
        return "views/group";
    }

    @RequestMapping("/views/updateGroup")
    public String updateGroup(@RequestParam("name") String name, @RequestParam("id") int id, Model model) {
        Group group = groupService.findGroupById(id);
        group.setName(name);
        groupService.updateGroup(group);
        model.addAttribute("groups", groupService.findAllGroups());
        logger.info("group " + id + " updated");
        return "views/allGroups";
    }

    @RequestMapping("/views/deleteGroup")
    public String deleteGroup(@RequestParam("id") int id, Model model) {
        groupService.deleteGroup(id);
        model.addAttribute("groups", groupService.findAllGroups());
        logger.info("group " + id + " deleted");
        return "views/allGroups";
    }

}
