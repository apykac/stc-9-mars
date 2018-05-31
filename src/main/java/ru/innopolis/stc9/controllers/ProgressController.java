package ru.innopolis.stc9.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.stc9.service.ProgressService;
import ru.innopolis.stc9.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Controller
@RequestMapping(value = "/views/progress")
public class ProgressController {
    @Autowired
    private ProgressService progressService;
    @Autowired
    private UserService userService;

    private int greaterOrEqualMark = 0;
    private int lessOrEqualMark = 5;

    /**
     * Выводим весь прогресс, все оценки
     *
     * @param session из сессии получаем логин
     */
    @RequestMapping(method = RequestMethod.GET)
    private String doGet(HttpSession session, Model model) {
        String login = (String) session.getAttribute("login");
        model.addAttribute("progress",
                progressService.getProgress(greaterOrEqualMark, lessOrEqualMark, userService.findUserByLogin(login)));
        return "views/progress";
    }

    /**
     * Выводим прогресс, по полученным оценкам
     *
     * @param marks   строка с оценками больше меньше, разделенные "-"
     * @param session сессия нужна чтобы передать значение в метод doGet
     */
    @RequestMapping(value = "/selmarks")
    private String getProgressBySelectedMark(@RequestParam String marks, HttpSession session, Model model) {
        greaterOrEqualMark = getMarks(marks)[0];
        lessOrEqualMark = getMarks(marks)[1];
        return doGet(session, model);
    }

    /**
     * Получаем на вход строку, сплитим по разделителю
     *
     * @param marks строка с оценками больше меньше
     * @return массив оценок больше меньше
     */
    private int[] getMarks(String marks) {
        return Arrays.stream(marks.split("\\-")).mapToInt(Integer::parseInt).toArray();
    }
}