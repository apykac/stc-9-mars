package ru.innopolis.stc9.service;

import ru.innopolis.stc9.pojo.Mark;

import java.util.Map;

public interface MarkService {
    public Map<String, Mark> getMarksByLessonId(int lessonId);
}
