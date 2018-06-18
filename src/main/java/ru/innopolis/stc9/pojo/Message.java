package ru.innopolis.stc9.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Message implements DBObject {
    @Getter
    @Setter
    @Id
    @SequenceGenerator(name = "messageSeq", sequenceName = "MESSAGE_SEQUENCE", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messageSeq")
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
