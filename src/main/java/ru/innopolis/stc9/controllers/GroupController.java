package ru.innopolis.stc9.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.stc9.pojo.Group;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.GroupService;
import ru.innopolis.stc9.service.UserService;


/**
 * Created by Сергей on 23.05.2018.
 * Выводит список всех групп и форму создания новой группы, а так же управляет студентами в группе
 */
@Controller
public class GroupController {
    private final Logger logger = Logger.getLogger(GroupController.class);
    private String allGroups = "views/allGroups";
    private String group = "views/group";
    private String attributeGroups = "groups";
    private String attributeGroupName = "groupName";
    private String attributeStudents = "students";
    private String attributeStudentsWOG = "studentsWOG";
    private String loggerPrefix = "group ";
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;

    /**
     * выводи список всех групп
     */
    @RequestMapping("/university/teacher/allgroup")
    public String viewAllGroups(Model model) {
        model.addAttribute(attributeGroups, groupService.findAllGroups());
        logger.info("view all groups");
        return allGroups;
    }

    /**
     * добавляет новую группу
     *
     * @param name имя группы
     */
    @RequestMapping("/university/teacher/addgroups")
    public String addGroup(@RequestParam("name") String name, Model model) {
        Group tempGroup = new Group(name);
        for (Group g : groupService.findAllGroups()) {
            if (g.getName().equals(name)) {
                model.addAttribute("errorName", "Такое имя группы уже используется. Введите другое");
                model.addAttribute(attributeGroups, groupService.findAllGroups());
                logger.info("duplicate names to adding ");
                return allGroups;
            }
        }
        groupService.addGroup(tempGroup);
        logger.info("group " + name + " added");
        model.addAttribute(attributeGroups, groupService.findAllGroups());
        return allGroups;
    }

    /**
     * открывает страницу управления группой: переименование или удаление группы,
     * добавление и удаление студентов в группе
     *
     * @param id - идентификатор группы
     */
    @RequestMapping("/university/teacher/group/{id}")
    public String forUpdateGroup(@PathVariable("id") int id, Model model) {

        model.addAttribute(attributeGroupName, groupService.findGroupById(id).getName());
        model.addAttribute("id", id);
        model.addAttribute(attributeStudents, userService.getStudentsByGroupId(id));
        model.addAttribute(attributeStudentsWOG, userService.getStudentsWithoutGroup(id));
        logger.info("group for update");
        return group;
    }

    /**
     * обновление имени группы
     *
     * @param name - имя группы
     * @param id   - идентификатор
     */
    @RequestMapping("/university/teacher/updateGroup")
    public String updateGroup(@RequestParam("name") String name, @RequestParam("id") int id, Model model) {
        Group tempGroup = groupService.findGroupById(id);
        tempGroup.setName(name);

        //проверка имени группы на повтор:
        for (Group g : groupService.findAllGroups()) {
            if (g.getName().equals(name) && g.getId() != id) {
                model.addAttribute("errorName", "Такое имя группы уже используется. Введите другое");
                model.addAttribute(attributeGroupName, groupService.findGroupById(id).getName());
                model.addAttribute("id", id);
                model.addAttribute(attributeStudents, userService.getStudentsByGroupId(id));
                model.addAttribute(attributeStudentsWOG, userService.getStudentsWithoutGroup(id));
                logger.info("duplicate names");
                return group;
            }
        }
        groupService.updateGroup(tempGroup);
        model.addAttribute(attributeGroups, groupService.findAllGroups());
        logger.info(loggerPrefix + id + " updated");
        return allGroups;
    }

    /**
     * удаляет группу
     *
     * @param id - идентификатор удаляемой группы
     */
    @RequestMapping("/university/teacher/deleteGroup")
    public String deleteGroup(@RequestParam("id") int id, Model model) {
        groupService.deleteGroup(id);
        model.addAttribute(attributeGroups, groupService.findAllGroups());
        logger.info(loggerPrefix + id + " deleted");
        return allGroups;
    }

    /**
     * добавляет студента в группу
     *
     * @param id        - идентификатор группы
     * @param studentId - идентификатор студента
     */
    @RequestMapping("/university/teacher/addStudent")
    public String addStudentToGroup(@RequestParam("id") int id, @RequestParam("studentId") int studentId, Model model) {
        User user = userService.findUserById(studentId);
        user.setGroupId(id);
        user.setGroup(groupService.findGroupById(id));
        userService.updateUser(user);
        model.addAttribute(attributeGroupName, groupService.findGroupById(id).getName());
        model.addAttribute("id", id);
        model.addAttribute(attributeStudents, userService.getStudentsByGroupId(id));
        model.addAttribute(attributeStudentsWOG, userService.getStudentsWithoutGroup(id));
        logger.info("student added in group " + id);
        return group;
    }

    /**
     * удаляет студента из группы
     *
     * @param id        - идентификатор группы
     * @param studentId - идентификатор студента
     */
    @RequestMapping("/university/teacher/group/deleteStudentFromGroup/{id}/{studentId}")
    public String deleteStudentFromGroup(@PathVariable("id") int id, @PathVariable("studentId") int studentId, Model model) {
        User user = userService.findUserById(studentId);
        user.setGroupId(0);
        userService.updateUser(user);
        model.addAttribute(attributeGroupName, groupService.findGroupById(id).getName());
        model.addAttribute("id", id);
        model.addAttribute(attributeStudents, userService.getStudentsByGroupId(id));
        model.addAttribute(attributeStudentsWOG, userService.getStudentsWithoutGroup(id));
        logger.info("student deleted from group " + id);
        return group;
    }
}
