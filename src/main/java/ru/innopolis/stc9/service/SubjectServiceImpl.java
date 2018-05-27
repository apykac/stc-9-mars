package ru.innopolis.stc9.service;

import ru.innopolis.stc9.dao.SubjectDao;
import ru.innopolis.stc9.dao.SubjectDaoImpl;
import ru.innopolis.stc9.pojo.Subject;

import java.util.List;

public class SubjectServiceImpl implements SubjectService {
    private SubjectDao subjectDao = new SubjectDaoImpl();

    @Override
    public List<Subject> findAllSubject() {
        return subjectDao.findAllSubject();
    }
}
