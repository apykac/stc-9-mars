package ru.innopolis.stc9.pojo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Created by Сергей on 31.05.2018.
 */
public class HomeWork {
    private int id;
    private String homeWorkURL;
    private int studentId;
    private int lessonId;

    public HomeWork(int id, String homeWorkURL, int studentId, int lessonId) {
        this.id = id;
        this.homeWorkURL = homeWorkURL;
        this.studentId = studentId;
        this.lessonId = lessonId;
    }

    public HomeWork(String homeWorkURL, int studentId, int lessonId) {
        this.homeWorkURL = homeWorkURL;
        this.studentId = studentId;
        this.lessonId = lessonId;
    }

    public HomeWork() {
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHomeWorkURL() {
        return homeWorkURL;
    }

    public void setHomeWorkURL(String homeWorkURL) {
        this.homeWorkURL = homeWorkURL;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        HomeWork homeWork = (HomeWork) o;

        return new EqualsBuilder()
                .append(getId(), homeWork.getId())
                .append(getStudentId(), homeWork.getStudentId())
                .append(getLessonId(), homeWork.getLessonId())
                .append(getHomeWorkURL(), homeWork.getHomeWorkURL())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getHomeWorkURL())
                .append(getStudentId())
                .append(getLessonId())
                .toHashCode();
    }
}
