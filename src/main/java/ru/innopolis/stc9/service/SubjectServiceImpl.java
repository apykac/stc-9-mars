package ru.innopolis.stc9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.stc9.dao.SubjectDao;
import ru.innopolis.stc9.pojo.Subject;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectDao subjectDao;

    @Override
    public boolean addSubject(Subject subject) {
        if (subject == null) return false;
        return subjectDao.addSubject(subject);
    }

    @Override
    public boolean deleteSubject(int subjectId) {
        if (subjectId < 0) return false;
        return subjectDao.deleteSubject(subjectId);
    }

    @Override
    public List<Subject> findAllSubject() {
        return subjectDao.findAllSubject();
    }
}
