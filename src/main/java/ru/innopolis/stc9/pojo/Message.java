package ru.innopolis.stc9.pojo;

public class Message implements DBObject {
    private int id;
    private int userId;
    private String text;
    private String toUserGroup;
    private String uname;
    private String theme;
    private Integer toUserId;

    public Message() {
        this.text = "";
        this.toUserGroup = "ROLE_ADMIN";
        this.uname = "";
        this.theme = "";
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

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }
}
