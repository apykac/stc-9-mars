package ru.innopolis.stc9.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.innopolis.stc9.pojo.Mark;
import ru.innopolis.stc9.service.interfaces.ProgressService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/university/progress")
public class ProgressController {
    private final ProgressService progressService;

    private int greaterOrEqualMark = 0;
    private int lessOrEqualMark = 5;

    @Autowired
    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    /**
     * Выводим весь прогресс, все оценки
     */
    @RequestMapping(method = RequestMethod.GET)
    private String doGet(HttpSession session, Model model) {
        String login = (String) session.getAttribute(SessionDataInform.LOGIN);
        List<Mark> markList = progressService.getProgress(greaterOrEqualMark, lessOrEqualMark, login);
        model.addAttribute("progress", markList);
        model.addAttribute("amountMarks", progressService.getAmountMarks(login));
        model.addAttribute("lessons", progressService.getLessons());
        model.addAttribute("missedLessons", progressService.getNumberOfMissedLessons(login));
        return "views/progress";
    }

    /**
     * Выводим прогресс, по полученным оценкам
     *
     * @param marks   строка с оценками больше меньше, разделенные "-"
     */
    @RequestMapping(value = "/selmarks")
    private String getProgressBySelectedMark(@RequestParam String marks, HttpSession session, Model model) {
        greaterOrEqualMark = getMarks(marks)[0];
        lessOrEqualMark = getMarks(marks)[1];
        return doGet(session, model);
    }

    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    private ModelAndView getPdf(HttpSession session) {
        String login = (String) session.getAttribute(SessionDataInform.LOGIN);
        List<Mark> markList = progressService.getProgress(greaterOrEqualMark, lessOrEqualMark, login);

        return new ModelAndView("progressPdf", "getpdf", markList);
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
