package ru.innopolis.stc9.dao;

import ru.innopolis.stc9.pojo.Progress;
import ru.innopolis.stc9.pojo.User;

import java.util.List;

public interface ProgressDao {
    List<Progress> getProgress(int greaterOrEqualMark, int lessOrEqualMark, User user);
}
