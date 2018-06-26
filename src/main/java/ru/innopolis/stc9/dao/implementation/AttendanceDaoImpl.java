package ru.innopolis.stc9.dao.implementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.innopolis.stc9.dao.interfaces.AttendanceDao;
import ru.innopolis.stc9.dao.interfaces.LessonsDao;
import ru.innopolis.stc9.dao.interfaces.UserDao;
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
    @Autowired
    private LessonsDao lessonDao;
    @Autowired
    private UserDao userDao;

    @Override
    public boolean addLessonAttendance(int lessonId, int[] students) {
        boolean result = true;
        if (lessonId < 1 || students == null || students.length < 1)
            result = false;
        for (int studentId : students) {
            Attendance attendance = new Attendance();
            Lessons lesson = lessonDao.getLessonById(lessonId);
            User student = userDao.findUserByUserId(studentId);
            attendance.setLesson(lesson);
            attendance.setUser(student);
            attendance.setAttended(true);
            try (Session session = factory.openSession()) {
                session.beginTransaction();
                session.save(attendance);
                session.getTransaction().commit();
            }
        }
        return result;
    }

    @Override
    public boolean updateAttendance(Attendance attendance) {
        if (attendance == null) return false;
        Session session = factory.getCurrentSession();
        session.update(attendance);
        return true;
    }

   /*@Override
    public boolean updateAttendance(Attendance attendance) {
        if (attendance == null) return false;
        int result;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaUpdate<Attendance> criteria = builder.createCriteriaUpdate(Attendance.class);
            Root<Attendance> root = criteria.from(Attendance.class);
            criteria.set(root.get(AttendanceMapper.ATTENDED), attendance.isAttended()).
                    where(builder.and(
                            builder.equal(root.get("lesson").get("id"), attendance.getLesson().getId()),
                            builder.equal(root.get("user").get("id"), attendance.getUser().getId())
                    ));
            Transaction transaction = session.beginTransaction();
            result = session.createQuery(criteria).executeUpdate();
            transaction.commit();
        }
        return result != 0;
    }*/

    @Override
    public int getNumberOfMissedLessons(int id) {
        int result = 0;
        if (id <= 0) return result;
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Attendance> criteriaAttendance = builder.createQuery(Attendance.class);
            CriteriaQuery<Lessons> criteriaLessons = builder.createQuery(Lessons.class);

            Root<Attendance> rootAttendance = criteriaAttendance.from(Attendance.class);
            Root<Lessons> rootLesson = criteriaLessons.from(Lessons.class);

            criteriaAttendance.select(rootAttendance).
                    where(builder.equal(rootAttendance.get("user").get("id"), id));
            criteriaLessons.select(rootLesson);

            List<Lessons> totalLessons = session.createQuery(criteriaLessons).getResultList();
            List<Attendance> totalAttendance = session.createQuery(criteriaAttendance).getResultList();
            result = totalLessons.size() - totalAttendance.size();
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
}
