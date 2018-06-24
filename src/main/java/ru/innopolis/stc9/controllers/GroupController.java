package ru.innopolis.stc9.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.stc9.pojo.Group;
import ru.innopolis.stc9.service.interfaces.GroupService;
import ru.innopolis.stc9.service.interfaces.StudentService;
import ru.innopolis.stc9.service.interfaces.UserService;

import java.util.List;


/**
 * Created by Сергей on 23.05.2018.
 * Выводит список всех групп и форму создания новой группы, а так же управляет студентами в группе
 */
@Controller
public class GroupController {
    private final Logger logger = Logger.getLogger(GroupController.class);
    private String loggerPrefix = "group ";
    private List<Group> allGroupsList;
    private final GroupService groupService;
    private final UserService userService;
    private final StudentService studentService;

    @Autowired
    public GroupController(GroupService groupService, UserService userService, StudentService studentService) {
        this.groupService = groupService;
        this.userService = userService;
        this.studentService = studentService;
    }

    /**
     * выводи список всех групп
     */
    @RequestMapping("/university/teacher/allgroup")
    public String viewAllGroups(Model model, @RequestParam(required = false) List<Group> groupsList) {
        allGroupsList = ((groupsList) == null || groupsList.isEmpty()) ? groupService.findAllGroups() : groupsList;
        model.addAttribute("groups", allGroupsList);
        logger.info("view all groups");
        return "views/allGroups";
    }

    /**
     * добавляет новую группу
     *
     * @param name имя группы
     */
    @RequestMapping("/university/teacher/addgroups")
    public String addGroup(@RequestParam("name") String name, Model model) {
        Group tempGroup = new Group(name);
        allGroupsList = groupService.findAllGroups();
        if (studentService.isDuplicate(allGroupsList, model, name)) return viewAllGroups(model, allGroupsList);
        groupService.addGroup(tempGroup);
        logger.info(loggerPrefix + name + " added");
        return viewAllGroups(model, null);
    }

    /**
     * открывает страницу управления группой: переименование или удаление группы,
     * добавление и удаление студентов в группе
     *
     * @param id - идентификатор группы
     */
    @RequestMapping("/university/teacher/group/{id}")
    public String forUpdateGroup(@PathVariable("id") int id,
                                 @RequestParam(value = "groupStatus", defaultValue = "0") int filterId,
                                 Model model) {
        if (!groupService.isEntityFound(id, 0)) {
            return error404Keeper();
        }
        studentService.addingMainAttributeToModel(model, id, filterId);
        logger.info("group for update");
        return "views/group";
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
        allGroupsList = groupService.findAllGroups();
        if (studentService.isDuplicate(allGroupsList, model, name)) return forUpdateGroup(id, 0, model);
        groupService.updateGroup(tempGroup);
        logger.info(loggerPrefix + id + " updated");
        return viewAllGroups(model, null);
    }

    /**
     * удаляет группу
     *
     * @param id - идентификатор удаляемой группы
     */
    @RequestMapping("/university/teacher/deleteGroup")
    public String deleteGroup(@RequestParam("id") int id, Model model) {
        groupService.deleteGroup(id);
        logger.info(loggerPrefix + id + " deleted");
        return viewAllGroups(model, null);
    }

    /**
     * добавляет студента в группу
     *
     * @param id        - идентификатор группы
     * @param studentId - идентификатор студента
     */
    @RequestMapping("/university/teacher/addStudent")
    public String addStudentToGroup(@RequestParam("id") int id, @RequestParam("studentId") int studentId, Model model) {
        userService.updateGroupId(studentId, id);
        logger.info("student added in group " + id);
        return forUpdateGroup(id, 0, model);
    }

    /**
     * удаляет студента из группы
     *
     * @param id        - идентификатор группы
     * @param studentId - идентификатор студента
     */
    @RequestMapping("/university/teacher/group/deleteStudentFromGroup/{id}/{studentId}")
    public String deleteStudentFromGroup(@PathVariable("id") int id, @PathVariable("studentId") int studentId, Model model) {
        if (!groupService.isEntityFound(id, studentId)) {
            return error404Keeper();
        }
        userService.updateGroupId(studentId, null);
        logger.info("student deleted from group " + id);
        return forUpdateGroup(id, 0, model);
    }

    //обработчик ошибки error 404
    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String error404Keeper(){
        return "error404";
    }

}
