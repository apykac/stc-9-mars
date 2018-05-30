package ru.innopolis.stc9.pojo;

public class Mark {
    private int id;
    private int lessonId;
    private int userId;
    private int value;

    public Mark(int userId, int lessonId, int value) {
        this.userId = userId;
        this.lessonId = lessonId;
        this.value = value;
    }

    public Mark() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
