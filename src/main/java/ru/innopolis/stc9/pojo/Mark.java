package ru.innopolis.stc9.pojo;

public class Mark implements DBObject {
    private int id;
    private int value;
    private int userId;
    private int lessonId;
    private String comment;

    public Mark() {
    }

    public Mark(int id, int value, int userId, int lessonId, String comment) {
        this.id = id;
        this.value = value;
        this.userId = userId;
        this.lessonId = lessonId;
        this.comment = comment;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
