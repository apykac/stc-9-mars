package ru.innopolis.stc9.pojo;

import java.util.Date;

public class Lessons {
    private int id;
    private int subject_id;
    private Date begin;
    private String lesson;

    public Lessons(int id, int subject_id, Date begin, String lesson) {
        this.id = id;
        this.subject_id = subject_id;
        this.begin = begin;
        this.lesson = lesson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }
}
