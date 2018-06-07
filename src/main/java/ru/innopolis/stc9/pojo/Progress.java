package ru.innopolis.stc9.pojo;

import java.sql.Date;

public class Progress implements DBObject {
    private int id;
    private int value;
    private String firstName;
    private String secondName;
    private String lessonsName;
    private Date date;
    private String subjectName;
    private String groupName;
    private String login;

    public Progress() {
    }

    public Progress(int id, int value, String firstName, String secondName, String lessonsName, Date date,
                    String subjectName, String groupName, String login) {
        this.id = id;
        this.value = value;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lessonsName = lessonsName;
        this.date = date;
        this.subjectName = subjectName;
        this.groupName = groupName;
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLessonsName() {
        return lessonsName;
    }

    public void setLessonsName(String lessonsName) {
        this.lessonsName = lessonsName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
