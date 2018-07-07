package ru.innopolis.stc9.pojo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@NoArgsConstructor
public class Message {
    @Getter
    @Setter
    private int id;
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
    private User toUser;
    @Getter
    @Setter
    private User fromUser;
}
