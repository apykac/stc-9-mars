package ru.innopolis.stc9.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
public class Progress {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private int value;
    @Getter
    @Setter
    private String firstName;
    @Getter
    @Setter
    private String secondName;
    @Getter
    @Setter
    private String lessonsName;
    @Getter
    @Setter
    private Date date;
    @Getter
    @Setter
    private String subjectName;
    @Getter
    @Setter
    private String groupName;
    @Getter
    @Setter
    private String login;
}
