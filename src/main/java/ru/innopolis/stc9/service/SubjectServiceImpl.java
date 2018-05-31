package ru.innopolis.stc9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.stc9.dao.SubjectDao;
import ru.innopolis.stc9.pojo.Education;
import ru.innopolis.stc9.pojo.Subject;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private EducationService educationService;

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
}
