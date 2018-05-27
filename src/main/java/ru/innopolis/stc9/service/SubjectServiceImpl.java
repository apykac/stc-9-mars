package ru.innopolis.stc9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.stc9.dao.SubjectDao;
import ru.innopolis.stc9.dao.SubjectDaoImpl;
import ru.innopolis.stc9.pojo.Subject;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectDao subjectDao = new SubjectDaoImpl();

    @Override
    public List<Subject> findAllSubject() {
        return subjectDao.findAllSubject();
    }
}
