package ru.innopolis.stc9.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc9.dao.interfaces.SubjectDao;
import ru.innopolis.stc9.pojo.Subject;
import ru.innopolis.stc9.service.interfaces.SubjectService;

import java.util.List;

@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {
    private final SubjectDao subjectDao;

    @Autowired
    public SubjectServiceImpl(SubjectDao subjectDao) {
        this.subjectDao = subjectDao;
    }

    @Override
    public boolean addSubject(String name) {
        if ("".equals(name)) {
            return false;
        } else {
            Subject subject = new Subject(name);
            return subjectDao.addSubject(subject);
        }
    }

    @Override
    public boolean deleteSubject(int subjectId) {
        if (subjectId < 0) {
            return false;
        } else {
            return subjectDao.deleteSubject(subjectId);
        }
    }

    @Override
    public List<Subject> findAllSubject() {
        return subjectDao.findAllSubject();
    }

    @Override
    public Subject findById(int id) {
        return subjectDao.findById(id);
    }


    /**
     * Проверка на совпадение
     *
     * @param name название предмета
     */
    @Override
    public boolean checkSubjectName(String name) {
        boolean existSubject = false;
        for (Subject subject : findAllSubject()) {
            if (name.equals(subject.getName())) {
                existSubject = true;
            }
        }
        return existSubject;
    }
}
