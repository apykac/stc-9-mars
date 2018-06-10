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
import ru.innopolis.stc9.service.interfaces.MessageService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    MessageService messageService;

    @RequestMapping(value = "/university/profile/feedback", method = RequestMethod.GET)
    public String feedbackGet(Model model) {
        return "/views/feedbackPage";
    }

    @RequestMapping(value = "/university/profile/feedback", method = RequestMethod.POST)
    public String feedbackPost(@RequestBody MultiValueMap<String, String> incParam,
                               HttpSession session,
                               Model model) {
        incParam.add("user_id", session.getAttribute(SessionDataInform.ID).toString());
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
        int currentUserId = (int) session.getAttribute(SessionDataInform.ID);
        String currentUserRole = (String) session.getAttribute(SessionDataInform.ROLE);
        List<Message>[] commonMessagesList = messageService.getAllMessages(currentUserId, currentUserRole);
        session.setAttribute(SessionDataInform.MSG, commonMessagesList[0].size() + commonMessagesList[1].size());
        model.addAttribute("commonList", commonMessagesList[0] == null ? new ArrayList<>() : commonMessagesList[0]);
        model.addAttribute("privateList", commonMessagesList[1] == null ? new ArrayList<>() : commonMessagesList[1]);
        return "/views/allMessages";
    }

    @RequestMapping(value = "/university/messages/{id}", method = RequestMethod.GET)
    public String editMessageGet(@PathVariable("id") int id, Model model) {
        return "";
    }
}
