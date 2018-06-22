package ru.innopolis.stc9.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.stc9.dao.interfaces.ProgressDao;
import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.pojo.Progress;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.interfaces.AttendanceService;
import ru.innopolis.stc9.service.interfaces.LessonsService;
import ru.innopolis.stc9.service.interfaces.ProgressService;
import ru.innopolis.stc9.service.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProgressServiceImpl implements ProgressService {
    private final ProgressDao progressDao;
    private final UserService userService;
    private final LessonsService lessonsService;
    private final AttendanceService attendanceService;

    @Autowired
    public ProgressServiceImpl(ProgressDao progressDao, UserService userService, LessonsService lessonsService, AttendanceService attendanceService) {
        this.progressDao = progressDao;
        this.userService = userService;
        this.lessonsService = lessonsService;
        this.attendanceService = attendanceService;
    }

    /**
     * Получаем количество оценок
     */
    @Override
    public List<Integer> getAmountMarks(String login) {
        List<Progress> progressList = getResultList(progressDao.getProgress(), login);
        List<Integer> marks = new ArrayList<>();
        marks.add(0, selectMarksInProgressList(0, 5, progressList).size());
        for (int i = 1; i < 6; i++) {
            int amountMarks = selectMarksInProgressList(i, i, progressList).size();
            marks.add(i, amountMarks);
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
    public List<Progress> getProgress(int greaterOrEqualMark, int lessOrEqualMark, String login) {
        List<Progress> progressList =
                selectMarksInProgressList(greaterOrEqualMark, lessOrEqualMark, progressDao.getProgress());
        return getResultList(progressList, login);
    }

    @Override
    public List<Lessons> getLessons() {
        return lessonsService.findAllLessons();
    }

    @Override
    public int getNumberOfMissedLessons(String login) {
        long id = getUser(login).getId();
        return attendanceService.getNumberOfMissedLessons(id);
    }

    /**
     * Из session вытаскиваем роль и логин и отбираем список по этим параметрам
     */
    private List<Progress> getResultList(List<Progress> progressList, String login) {
        String role = getUser(login).getPermissionGroup();
        if (isStudent(role)) {
            return progressByLogin(login, progressList);
        }
        return progressList;
    }

    private User getUser(String login) {
        return userService.findUserByLogin(login);
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
