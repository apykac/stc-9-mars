package ru.innopolis.stc9.service.implementation;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.innopolis.stc9.dao.interfaces.LessonsDao;
import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.service.interfaces.LessonsService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@Transactional
public class LessonsServiceImpl implements LessonsService {
    private final Logger logger = Logger.getLogger(LessonsServiceImpl.class);
    private final LessonsDao lessonsDao;

    @Autowired
    public LessonsServiceImpl(LessonsDao lessonsDao) {
        this.lessonsDao = lessonsDao;
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
    public List<Lessons> findAllLessonsWithSubjects() {
        return lessonsDao.findAllLessonsWithSubjects();
    }

    @Override
    public List<Lessons> findAllLessonsByWithSubject(int subjectId) {
        return lessonsDao.findAllLessonsByWithSubject(subjectId);
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
