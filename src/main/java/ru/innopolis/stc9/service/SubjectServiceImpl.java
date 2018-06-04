package ru.innopolis.stc9.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.stc9.dao.SubjectDao;
import ru.innopolis.stc9.pojo.Education;
import ru.innopolis.stc9.pojo.Subject;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final Logger logger = Logger.getLogger(SubjectServiceImpl.class);
    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private EducationService educationService;

    @Override
    public boolean addSubject(String name) {
        if ("".equals(name)) return false;
        Subject subject = new Subject(name);
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

    @Override
    public Subject findById(int id) {
        return subjectDao.findById(id);
    }

    @Override
    public List<Subject> findByGroupId(int id) {
        ArrayList<Subject> subjectList = new ArrayList<>();
        for(Education e: educationService.findAllEducations()){
            if (e.getGroupId() == id){
                subjectList.add(findById(e.getSubjectId()));
            }
        }
        return subjectList;
    }

    /**
     * Проверка на совпадение
     *
     * @param name название предмета
     */
    public boolean checkSubjectName(String name) {
        boolean existSubject = false;

        for (Subject subject : findAllSubject()) {
            if (name.equals(subject.getName())) {
                existSubject = true;
            }
        }

        if (!existSubject) {
            addSubject(name);
        }
        return existSubject;
    }
}
