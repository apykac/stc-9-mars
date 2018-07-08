package ru.innopolis.stc9.service.implementation;

import org.springframework.stereotype.Service;
import ru.innopolis.stc9.pojo.Subject;
import ru.innopolis.stc9.service.interfaces.SubjectService;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Override
    public boolean addSubject(String name) {
        return false;
    }

    @Override
    public boolean deleteSubject(int subjectId) {
        return false;
    }

    @Override
    public List<Subject> findAllSubject() {
        return null;
    }

    @Override
    public Subject findById(int id) {
        return null;
    }

    @Override
    public boolean checkSubjectName(String name) {
        return false;
    }

    /*@Override
    public boolean addSubject(String name) {
        if ("".equals(name)) {
            return false;
        } else {
            Subject subject = new Subject(name);
            return subjectDao.addSubject(subject);
        }
    }*/

    /*@Override
    public boolean deleteSubject(int subjectId) {
        if (subjectId < 0) {
            return false;
        } else {
            return subjectDao.deleteSubject(subjectId);
        }
    }*/

    /*@Override
    public List<Subject> findAllSubject() {
        return subjectDao.findAllSubject();
    }*/

    /*@Override
    public Subject findById(int id) {
        return subjectDao.findById(id);
    }*/

    /*@Override
    public boolean checkSubjectName(String name) {
        boolean existSubject = false;
        for (Subject subject : findAllSubject()) {
            if (name.equals(subject.getName())) {
                existSubject = true;
            }
        }
        return existSubject;
    }*/
}
