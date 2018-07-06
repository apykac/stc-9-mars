package ru.innopolis.stc9.service.implementation;

import org.springframework.stereotype.Service;
import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.service.interfaces.LessonsService;

import java.sql.Date;
import java.util.List;

@Service
public class LessonsServiceImpl implements LessonsService {
    @Override
    public boolean addLesson(Lessons lesson) {
        return false;
    }

    @Override
    public boolean deleteLesson(int lessonId) {
        return false;
    }

    @Override
    public List<Lessons> findAllLessonsWithSubjects() {
        return null;
    }

    @Override
    public List<Lessons> findAllLessonsByWithSubject(int subjectId) {
        return null;
    }

    @Override
    public Date stringToDate(String strDate) {
        return null;
    }

    /*@Override
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
    }*/

    /*@Override
    public boolean deleteLesson(int lessonId) {
        if (lessonsDao.deleteLesson(lessonId)) {
            logger.info("lesson " + lessonId + " deleted");
            return true;
        } else {
            return false;
        }
    }*/

    /*@Override
    public List<Lessons> findAllLessonsWithSubjects() {
        return lessonsDao.findAllLessonsWithSubjects();
    }*/

    /*@Override
    public List<Lessons> findAllLessonsByWithSubject(int subjectId) {
        return lessonsDao.findAllLessonsByWithSubject(subjectId);
    }*/

    /*@Override
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
    }*/
}
