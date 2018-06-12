package ru.innopolis.stc9.service.interfaces;

import ru.innopolis.stc9.pojo.Subject;

import java.util.List;

public interface SubjectService {
    boolean addSubject(String name);

    boolean deleteSubject(int subjectId);

    List<Subject> findAllSubject();

    Subject findById(int id);

    List<Subject> findByGroupId(Integer id);

    boolean checkSubjectName(String name);
}
