package ru.innopolis.stc9.service.implementation;


import org.springframework.stereotype.Service;
import ru.innopolis.stc9.pojo.Mark;
import ru.innopolis.stc9.service.interfaces.ProgressService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProgressServiceImpl implements ProgressService {

    @Override
    public List<Mark> getProgress(int greaterOrEqualMark, int lessOrEqualMark, String role, String login) {
        return null;
    }

    @Override
    public List<Integer> getAmountMarks(String role, List<Mark> markList) {
        List<Integer> marks = new ArrayList<>();
        marks.add(0, selectMarksInMarkList(0, 5, markList).size());
        for (int i = 1; i < 6; i++) {
            int amountMarks = selectMarksInMarkList(i, i, markList).size();
            marks.add(i, amountMarks);
        }
        return marks;
    }

    /*@Override
    public List<Mark> getProgress(int greaterOrEqualMark, int lessOrEqualMark, String role, String login) {
        List<Mark> progressList =
                selectMarksInMarkList(greaterOrEqualMark, lessOrEqualMark, progressDao.getMark());
        return getResultList(progressList, role, login);
    }*/

    @Override
    //TODO переделать
    public int getNumberOfMissedLessons(String login) {
        //int id = getUser(login).getId();
        return 0;//attendanceService.getNumberOfMissedLessons(id);
    }

    private List<Mark> getResultList(List<Mark> markList, String role, String login) {
        if (isStudent(role)) {
            return markByLogin(login, markList);
        }
        return markList;
    }

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

    private List<Mark> markByLogin(String login, List<Mark> markList) {
        List<Mark> result = new ArrayList<>();
        for (Mark mark : markList) {
            if (login.equals(mark.getStudent().getLogin())) {
                result.add(mark);
            }
        }
        return result;
    }

    private boolean isStudent(String role) {
        return role.equals("ROLE_STUDENT");
    }
}
