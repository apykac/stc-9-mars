package ru.innopolis.stc9.dao;

import ru.innopolis.stc9.pojo.Subject;

import java.util.List;

public interface SubjectDao {
    List<Subject> findAllSubject();
}
