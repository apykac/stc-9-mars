package ru.innopolis.stc9.pojo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@EqualsAndHashCode
@NoArgsConstructor
public class User {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private String login;
    @Getter
    @Setter
    private String hashPassword;
    @Getter
    @Setter
    private String permissionGroup = "ROLE_STUDENT";
    @Getter
    @Setter
    private String firstName = "";
    @Getter
    @Setter
    private String secondName = "";
    @Getter
    @Setter
    private String middleName = "";
    @Getter
    @Setter
    private int enabled = 1;
    @Getter
    @Setter
    private Group group = null;
    @Getter
    @Setter
    private List<Message> incomingMessages;
    @Getter
    @Setter
    private List<Message> upcomingMessages;
    @Getter
    @Setter
    private List<Attendance> attendances;
    @Getter
    @Setter
    private List<HomeWork> homeWorks;
    @Getter
    @Setter
    private List<Mark> marks;

    public User(String login, String hashPassword, String firstName, String secondName, String middleName) {
        this.login = login;
        this.hashPassword = hashPassword;
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
    }
}
