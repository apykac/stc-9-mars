package ru.innopolis.stc9.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.stc9.dao.interfaces.LessonsDao;
import ru.innopolis.stc9.dao.interfaces.MarkDao;
import ru.innopolis.stc9.dao.interfaces.UserDao;
import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.pojo.Mark;
import ru.innopolis.stc9.pojo.User;
import ru.innopolis.stc9.service.interfaces.MarkService;

import java.util.HashMap;
import java.util.Map;

@Service
public class MarkServiceImpl implements MarkService {

    @Autowired
    private MarkDao markDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private LessonsDao lessonsDao;

    public MarkServiceImpl(MarkDao markDao, UserDao userDao, LessonsDao lessonsDao) {
        this.markDao = markDao;
        this.userDao = userDao;
        this.lessonsDao = lessonsDao;
    }

    public MarkServiceImpl() {
    }

    /**
     * Возвращает Map с именами студентов и оценкой ДЗ, связанного с уроком,
     * id которого передается параметром метода.
     *
     * @param lessonId
     * @return
     */
    @Override
    public Map<String, Mark> getMarksByLessonId(int lessonId) {
        Map<String, Mark> result = new HashMap<>();
        if (lessonId < 1) {
            return result;
        }
        for (Mark mark : markDao.getMarksByLessonId(lessonId)) {
            User student = userDao.findUserByUserId(mark.getStudent().getId());
            String name = getFullStudentName(student);
            result.put(name, mark);
        }
        return result;
    }

    @Override
    public Mark getMarkById(int id) {
        return markDao.getMarkById(id);
    }

    @Override
    public boolean updateMark(Mark mark) {
        return markDao.updateMark(mark);
    }

    @Override
    public String getFullStudentNameInOneString(int markId) {
        Mark mark = getMarkById(markId);
        User user = userDao.findUserByUserId(mark.getStudent().getId());
        return user.getSecondName() +
                " " +
                user.getFirstName() +
                " " +
                user.getMiddleName();
    }

    @Override
    public String getLessonNameByMarkId(int markId) {
        Mark mark = getMarkById(markId);
        Lessons lesson = lessonsDao.getLessonById(mark.getLesson().getId());
        return lesson.getName();
    }

    @Override
    public String getLessonNameByLessonId(int lessonId) {
        Lessons lesson = lessonsDao.getLessonById(lessonId);
        return lesson.getName();
    }

    @Override
    public String getFullStudentName(User student) {
        if (student == null) {
            return "";
        }
        return student.getSecondName() +
                " " +
                student.getFirstName() +
                " " +
                student.getMiddleName();
    }

}
