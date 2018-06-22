package ru.innopolis.stc9.service.interfaces;

import ru.innopolis.stc9.pojo.Subject;

import java.util.List;

public interface SubjectService {
    boolean addSubject(String name);

    boolean deleteSubject(long subjectId);

    List<Subject> findAllSubject();

    Subject findById(long id);

    List<Subject> findByGroupId(Long id);

    boolean checkSubjectName(String name);
}
