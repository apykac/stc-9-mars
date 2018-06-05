package ru.innopolis.stc9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.innopolis.stc9.controllers.SessionDataInform;
import ru.innopolis.stc9.dao.ProgressDao;
import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.pojo.Progress;
import ru.innopolis.stc9.pojo.User;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProgressServiceImpl implements ProgressService {
    @Autowired
    private ProgressDao progressDao;
    @Autowired
    private UserService userService;
    @Autowired
    private LessonsService lessonsService;
    @Autowired
    private AttendanceService attendanceService;

    /**
     * Получаем количество оценок
     */
    @Override
    public List<Integer> getAmountMarks(HttpSession session) {
        List<Progress> progressList = getResultList(progressDao.getProgress(), session);
        List<Integer> marks = new ArrayList<>();
        marks.add(0, selectMarksInProgressList(0, 5, progressList).size());
        for (int i = 1; i < 6; i++) {
            marks.add(i, selectMarksInProgressList(i, i, progressList).size());
        }
        return marks;
    }

    /**
     * Список с отфильтрованными логином и оценками
     *
     * @param greaterOrEqualMark Оценка больше или равно
     * @param lessOrEqualMark    Оценка меньше или равно
     */
    @Override
    public List<Progress> getProgress(int greaterOrEqualMark, int lessOrEqualMark, HttpSession session) {
        List<Progress> progressList =
                selectMarksInProgressList(greaterOrEqualMark, lessOrEqualMark, progressDao.getProgress());
        return getResultList(progressList, session);
    }

    @Override
    public List<Lessons> getLessons() {
        return lessonsService.findAllLessons();
    }

    @Override
    public int getNumberOfMissedLessons() {
        return attendanceService.getNumberOfMissedLessons(getUser().getId());
    }

    /**
     * Из контекста получаем User, вытаскиваем роль и логин и отбираем список по этим параметрам
     */
    private List<Progress> getResultList(List<Progress> progressList, HttpSession session) {
        String login = getUser(session).getLogin();
        String role = getUser(session).getPermissionGroup();
        if (isStudent(role)) {
            return progressByLogin(login, progressList);
        }
        return progressList;
    }

    private User getUser(HttpSession session) {
        return userService.findUserByLogin((String) session.getAttribute(SessionDataInform.LOGIN));
    }

    /**
     * Отбираем по @param greaterOrEqualMark @param lessOrEqualMark
     *
     * @param greaterOrEqualMark Оценка больше или равно
     * @param lessOrEqualMark    Оценка меньше или равно
     * @param progressList       изначальный список
     * @return Возвращаем отфильтрованный по оценкам список
     */
    private List<Progress> selectMarksInProgressList(int greaterOrEqualMark,
                                                     int lessOrEqualMark,
                                                     List<Progress> progressList) {
        List<Progress> result = new ArrayList<>();
        for (Progress progress : progressList) {
            int mark = progress.getValue();
            if (mark >= greaterOrEqualMark && mark <= lessOrEqualMark) {
                result.add(progress);
            }
        }
        return result;
    }

    /**
     * Отбираем по @param login
     *
     * @param login        логин пользователя
     * @param progressList Отфильтрованный список оценок
     * @return Возвращаем отфильтрованный по логину список оценок
     */
    private List<Progress> progressByLogin(String login, List<Progress> progressList) {
        List<Progress> result = new ArrayList<>();
        for (Progress progress : progressList) {
            if (login.equals(progress.getLogin())) {
                result.add(progress);
            }
        }
        return result;
    }

    /**
     * Проверяем является ли пользователь студентом
     *
     * @param role роль пользователя
     */
    private boolean isStudent(String role) {
        return role.equals("ROLE_STUDENT");
    }
}
