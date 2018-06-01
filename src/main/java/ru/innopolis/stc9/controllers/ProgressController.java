package ru.innopolis.stc9.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.stc9.pojo.Progress;
import ru.innopolis.stc9.service.ProgressService;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/views/progress")
public class ProgressController {
    @Autowired
    private ProgressService progressService;

    private int greaterOrEqualMark = 0;
    private int lessOrEqualMark = 5;

    /**
     * Выводим весь прогресс, все оценки
     */
    @RequestMapping(method = RequestMethod.GET)
    private String doGet(Model model) {
        List<Progress> listProgress = progressService.getProgress(greaterOrEqualMark, lessOrEqualMark);
        model.addAttribute("progress", listProgress);
        return "views/progress";
    }

    /**
     * Выводим прогресс, по полученным оценкам
     *
     * @param marks   строка с оценками больше меньше, разделенные "-"
     */
    @RequestMapping(value = "/selmarks")
    private String getProgressBySelectedMark(@RequestParam String marks, Model model) {
        greaterOrEqualMark = getMarks(marks)[0];
        lessOrEqualMark = getMarks(marks)[1];
        return doGet(model);
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
