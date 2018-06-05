package ru.innopolis.stc9.service;

import ru.innopolis.stc9.pojo.Subject;

import java.util.List;

public interface SubjectService {
    boolean addSubject(Subject subject);

    boolean deleteSubject(int subjectId);

    List<Subject> findAllSubject();

    Subject findById(int id);

    List<Subject> findByGroupId(int id);
}
