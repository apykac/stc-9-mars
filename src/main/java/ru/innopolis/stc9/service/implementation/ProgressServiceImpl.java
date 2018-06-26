package ru.innopolis.stc9.service.implementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc9.dao.interfaces.ProgressDao;
import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.pojo.Mark;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.interfaces.AttendanceService;
import ru.innopolis.stc9.service.interfaces.LessonsService;
import ru.innopolis.stc9.service.interfaces.ProgressService;
import ru.innopolis.stc9.service.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
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
        List<Mark> progressList = getResultList(progressDao.getMark(), login);
        List<Integer> marks = new ArrayList<>();
        marks.add(0, selectMarksInMarkList(0, 5, progressList).size());
        for (int i = 1; i < 6; i++) {
            int amountMarks = selectMarksInMarkList(i, i, progressList).size();
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
    public List<Mark> getProgress(int greaterOrEqualMark, int lessOrEqualMark, String login) {
        List<Mark> progressList =
                selectMarksInMarkList(greaterOrEqualMark, lessOrEqualMark, progressDao.getMark());
        return getResultList(progressList, login);
    }

    @Override
    public List<Lessons> getLessons() {
        return lessonsService.findAllLessons();
    }

    @Override
    //TODO переделать
    public int getNumberOfMissedLessons(String login) {
        int id = getUser(login).getId();
        return 0;//attendanceService.getNumberOfMissedLessons(id);
    }

    /**
     * Из session вытаскиваем роль и логин и отбираем список по этим параметрам
     */
    private List<Mark> getResultList(List<Mark> markList, String login) {
        String role = getUser(login).getPermissionGroup();
        if (isStudent(role)) {
            return markByLogin(login, markList);
        }
        return markList;
    }

    private User getUser(String login) {
        return userService.findUserByLogin(login);
    }

    /**
     * Отбираем по @param greaterOrEqualMark @param lessOrEqualMark
     *
     * @param greaterOrEqualMark Оценка больше или равно
     * @param lessOrEqualMark Оценка меньше или равно
     * @param markList изначальный список
     * @return Возвращаем отфильтрованный по оценкам список
     */
    private List<Mark> selectMarksInMarkList(int greaterOrEqualMark,
                                             int lessOrEqualMark,
                                             List<Mark> markList) {
        List<Mark> result = new ArrayList<>();
        for (Mark mark : markList) {
            int tempMark = mark.getValue();
            if (tempMark >= greaterOrEqualMark && tempMark <= lessOrEqualMark) {
                result.add(mark);
            }
        }
        return result;
    }

    /**
     * Отбираем по @param login
     *
     * @param login логин пользователя
     * @param markList Отфильтрованный список оценок
     * @return Возвращаем отфильтрованный по логину список оценок
     */
    private List<Mark> markByLogin(String login, List<Mark> markList) {
        List<Mark> result = new ArrayList<>();
        for (Mark mark : markList) {
            if (login.equals(mark.getStudent().getLogin())) {
                result.add(mark);
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
