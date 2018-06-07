package ru.innopolis.stc9.dao.interfaces;

import ru.innopolis.stc9.pojo.Subject;

import java.util.List;

public interface SubjectDao {
    boolean addSubject(Subject subject);

    boolean deleteSubject(int subjectId);

    List<Subject> findAllSubject();

    Subject findById(int id);
}
