package ru.innopolis.stc9.pojo;

public class Message implements DBObject {
    private int id;
    private int userId;
    private String text;
    private String toUserGroup;

    public Message() {
        this.text = "";
        this.toUserGroup = "ROLE_ADMIN";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getToUserGroup() {
        return toUserGroup;
    }

    public void setToUserGroup(String toUserGroup) {
        this.toUserGroup = toUserGroup;
    }
}
