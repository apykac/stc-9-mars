package ru.innopolis.stc9.service.interfaces;

import ru.innopolis.stc9.pojo.Mark;

import java.util.List;

public interface ProgressService {
    List<Mark> getProgress(int greaterOrEqualMark, int lessOrEqualMark, String role, String login);

    List<Integer> getAmountMarks(String role, List<Mark> markList);

    int getNumberOfMissedLessons(String login);
}
