package ru.innopolis.stc9.service.implementation;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc9.dao.interfaces.LessonsDao;
import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.pojo.Subject;
import ru.innopolis.stc9.service.interfaces.LessonsService;
import ru.innopolis.stc9.service.interfaces.SubjectService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@Transactional
public class LessonsServiceImpl implements LessonsService {
    private final Logger logger = Logger.getLogger(LessonsServiceImpl.class);
    private final LessonsDao lessonsDao;
    private final SubjectService subjectService;

    @Autowired
    public LessonsServiceImpl(LessonsDao lessonsDao, SubjectService subjectService) {
        this.lessonsDao = lessonsDao;
        this.subjectService = subjectService;
    }


    @Override
    public boolean addLesson(Lessons lesson) {
        if (lesson.getName() == null) {
            return false;
        } else {
            if (lessonsDao.addLesson(lesson)) {
                logger.info("lesson (" + lesson.getName() + ") added");
                return true;
            } else
                return false;
        }
    }

    @Override
    public boolean deleteLesson(int lessonId) {
        if (lessonsDao.deleteLesson(lessonId)) {
            logger.info("lesson " + lessonId + " deleted");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Lessons> findAllLessons() {
        List<Subject> subjectList = subjectService.findAllSubject();
        List<Lessons> lessonsList = lessonsDao.findAllLessons();

        for (Lessons lessons : lessonsList) {
            lessons.setSname(
                    subjectList.get(lessons.getId())
                            .getName()
            );
        }

        return lessonsList;
    }

    @Override
    public java.sql.Date stringToDate(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date result = null;
        try {
            java.util.Date simpleDate = sdf.parse(strDate);
            result = new java.sql.Date(simpleDate.getTime());
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return result;
    }
}
