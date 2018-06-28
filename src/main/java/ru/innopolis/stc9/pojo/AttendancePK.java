package ru.innopolis.stc9.pojo;

import java.io.Serializable;
import java.util.Objects;

public class AttendancePK implements Serializable {
    protected Lessons lesson;
    protected User user;

    public AttendancePK() {
    }

    public AttendancePK(Lessons lesson, User user) {
        this.lesson = lesson;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttendancePK that = (AttendancePK) o;
        return Objects.equals(lesson, that.lesson) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {

        return Objects.hash(lesson, user);
    }
}
