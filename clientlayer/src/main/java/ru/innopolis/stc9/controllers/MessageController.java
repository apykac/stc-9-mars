package ru.innopolis.stc9.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.innopolis.stc9.pojo.Message;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.interfaces.MessageService;
import ru.innopolis.stc9.service.interfaces.UserService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MessageController {
    MessageService messageService;
    UserService userService;
    private String error = "error";

    @Autowired
    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @RequestMapping(value = "/university/profile/feedback", method = RequestMethod.GET)
    public String feedbackGet(Model model) {
        return "views/feedbackPage";
    }

    @RequestMapping(value = "/university/profile/feedback", method = RequestMethod.POST)
    public String feedbackPost(@RequestBody MultiValueMap<String, String> incParam,
                               HttpSession session,
                               Model model) {
        incParam.add("fromUserId", session.getAttribute(SessionDataInform.ID).toString());
        incParam.add("uname", "[" + session.getAttribute(SessionDataInform.LOGIN) + "] " +
                session.getAttribute(SessionDataInform.NAME));
        List<String> errors = messageService.isCorrectData(incParam);
        List<String> successList = new ArrayList<>();
        if (errors.isEmpty())
            if (!messageService.addMessage(incParam)) errors.add("Error adding by DAO");
            else successList.add("Сообщение отправлено");
        model.addAttribute("errors", errors);
        model.addAttribute("success_list", successList);
        return feedbackGet(model);
    }

    @RequestMapping(value = "/university/messages", method = RequestMethod.GET)
    public String messagesToPage(HttpSession session, Model model) {
        User user = userService.findUserById((int) session.getAttribute(SessionDataInform.ID));
        List<Message>[] commonMessagesList = messageService.getAllMessages(user);
        session.setAttribute(SessionDataInform.MSG, commonMessagesList[0].size() + commonMessagesList[1].size());
        model.addAttribute("commonList", commonMessagesList[0]);
        model.addAttribute("privateList", commonMessagesList[1]);
        return "views/allMessages";
    }

    @RequestMapping(value = "/university/messages/{id}", method = RequestMethod.GET)
    public String editMessageGet(@PathVariable("id") int id, Model model, HttpSession session) {
        Message message = messageService.getMessageByIdWithFromUser(id);
        model.addAttribute("message", message);
        model.addAttribute("fromUser", session.getAttribute(SessionDataInform.ID));
        return "views/messagePage";
    }

    @RequestMapping(value = "/university/messages/{id}/delete", method = RequestMethod.GET)
    public String deleteMessageGet() {
        return "redirect:/university/start";
    }

    @RequestMapping(value = "/university/messages/{id}/delete", method = RequestMethod.POST)
    public String deleteMessagePost(@PathVariable("id") int id, Model model, HttpSession session) {
        if (!messageService.deleteMessageById(id)) {
            model.addAttribute(error, "Fail to delete message by DAO");
            return editMessageGet(id, model, session);
        } else {
            session.setAttribute(SessionDataInform.MSG, (int) session.getAttribute(SessionDataInform.MSG) - 1);
            return "redirect:/university/start?message=deleted";
        }
    }

    @RequestMapping(value = "/university/messages/{id}/reply", method = RequestMethod.GET)
    public String replyMessageGet() {
        return "redirect:/university/start";
    }

    @RequestMapping(value = "/university/messages/{id}/reply", method = RequestMethod.POST)
    public String replyMessagePost(@PathVariable("id") int id,
                                   @RequestBody MultiValueMap<String, String> incParam,
                                   HttpSession session,
                                   Model model) {
        if (!messageService.isCorrectData(incParam).isEmpty())
            model.addAttribute(error, "Сообщение пустое, введите текст");
        else {

            tryToAddMessage(incParam, session, model);
        }
        return editMessageGet(id, model, session);
    }

    private void tryToAddMessage(MultiValueMap<String, String> incParam, HttpSession session, Model model) {
        incParam.add("uname", "[" + session.getAttribute(SessionDataInform.LOGIN) + "] " +
                session.getAttribute(SessionDataInform.NAME));
        if (!messageService.addMessage(incParam)) model.addAttribute(error, "Fail to add message by DAO");
        else model.addAttribute("success", "Сообщение отправлено успешно");
    }
}
