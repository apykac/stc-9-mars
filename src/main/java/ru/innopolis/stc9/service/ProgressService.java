package ru.innopolis.stc9.service;

import ru.innopolis.stc9.pojo.Progress;

import java.util.List;

public interface ProgressService {
    List<Progress> getProgress(int greaterOrEqualMark, int lessOrEqualMark);
}
