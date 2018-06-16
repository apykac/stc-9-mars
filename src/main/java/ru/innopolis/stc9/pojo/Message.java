package ru.innopolis.stc9.pojo;

import lombok.Getter;
import lombok.Setter;

public class Message implements DBObject {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private int userId;
    @Getter
    @Setter
    private String text;
    @Getter
    @Setter
    private String toUserGroup;
    @Getter
    @Setter
    private String uname;
    @Getter
    @Setter
    private String theme;
    @Getter
    @Setter
    private Integer toUserId;

    public Message() {
        this.text = "";
        this.toUserGroup = "ROLE_ADMIN";
        this.uname = "";
        this.theme = "";
    }
}
