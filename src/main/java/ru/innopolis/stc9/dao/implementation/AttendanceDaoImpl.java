package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc9.dao.interfaces.AttendanceDao;
import ru.innopolis.stc9.dao.mappers.UserMapper;
import ru.innopolis.stc9.pojo.Attendance;
import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.pojo.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AttendanceDaoImpl implements AttendanceDao {
    @Autowired
    private SessionFactory factory;

    @Override
    public boolean addLessonAttendance(int lessonId, int[] students) {
        boolean result = true;
        if (lessonId < 1 || students == null || students.length < 1)
            result = false;
        Session session = factory.getCurrentSession();
        for (int studentId : students) {
            Lessons lesson = session.load(Lessons.class, lessonId);
            User student = session.load(User.class, studentId);
            Attendance attendance = new Attendance(true, lesson, student);
            session.save(attendance);
        }
        return result;
    }

    /**
     * Возвращает список студентов из определенной группы, отмеченных ранее присутствующими на определенном уроке.
     *
     * @param groupId  id группы
     * @param lessonId id урока
     * @return объект List<User>
     */
    public List<Attendance> getLessonAttendance(int lessonId, int groupId) {
        List<Attendance> result = new ArrayList<>();
        if (groupId < 1 && lessonId < 1) {
            return result;
        }
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Attendance> criteria = builder.createQuery(Attendance.class);
            Root<Attendance> rootAttendance = criteria.from(Attendance.class);
            Root<User> rootUser = criteria.from(User.class);
            criteria.select(rootAttendance).
                    where(builder.and(
                            builder.equal(rootAttendance.get("user").get("id"), rootUser.get(UserMapper.ID)),
                            builder.and(
                                    builder.equal(rootAttendance.get("lesson").get("id"), lessonId),
                                    builder.equal(rootUser.get("group").get("id"), groupId)
                            )
                    ));
            result = session.createQuery(criteria).getResultList();
        }
        return result;
    }

    @Override
    public boolean deleteAttendance(Attendance attendance) {
        if (attendance == null) {
            return false;
        }
        Session session = factory.getCurrentSession();
        session.delete(attendance);
        return true;
    }
}
