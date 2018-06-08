package ru.innopolis.stc9.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
        List<String> errors = messageService.isCorrectData(incParam);
        List<String> successList = new ArrayList<>();
        if (errors.isEmpty())
            if (!messageService.addMessage(incParam)) errors.add("Error adding by DAO");
            else successList.add("Сообщение отправлено");
        model.addAttribute("errors", errors);
        model.addAttribute("success_list", successList);
        return feedbackGet(model);
    }
}
