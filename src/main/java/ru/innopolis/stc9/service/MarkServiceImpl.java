package ru.innopolis.stc9.service;

import org.springframework.stereotype.Service;
import ru.innopolis.stc9.dao.MarkDao;
import ru.innopolis.stc9.dao.MarkDaoImpl;
import ru.innopolis.stc9.dao.UserDao;
import ru.innopolis.stc9.dao.UserDaoImpl;
import ru.innopolis.stc9.pojo.Mark;

import java.util.HashMap;
import java.util.Map;

@Service
public class MarkServiceImpl implements MarkService {

    private MarkDao markDao = new MarkDaoImpl();
    private UserDao userDao = new UserDaoImpl();

    /**
     * Возвращает Map с именами студентов и оценками за работу на уроке,
     * id которого передается параметром метода.
     *
     * @param lessonId
     * @return
     */
    @Override
    public Map<String, Mark> getMarksByLessonId(int lessonId) {
        Map<String, Mark> result = new HashMap<>();
        for (Mark mark : markDao.getMarksByLessonId(lessonId)) {
            String fName = userDao.findUserByUserId(mark.getUserId()).getFirstName();
            String lName = userDao.findUserByUserId(mark.getUserId()).getSecondName();
            String mName = userDao.findUserByUserId(mark.getUserId()).getMiddleName();
            result.put(lName + fName + mName, mark);
        }
        return result;
    }

}
