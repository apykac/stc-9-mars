package ru.innopolis.stc9.service.interfaces;

import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.pojo.Progress;

import java.util.List;

public interface ProgressService {
    List<Progress> getProgress(int greaterOrEqualMark, int lessOrEqualMark, String login);

    List<Integer> getAmountMarks(String login);

    List<Lessons> getLessons();

    int getNumberOfMissedLessons(String login);
}
