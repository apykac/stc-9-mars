package ru.innopolis.stc9.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User implements DBObject {
    @Getter
    @Setter
    @Id
    @SequenceGenerator(name = "userSeq", sequenceName = "USER_SEQUENCE", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
    private int id;
    @Getter
    @Setter
    @Column(unique = true)
    private String login;
    @Getter
    @Setter
    private String hashPassword;
    @Getter
    @Setter
    private String permissionGroup;
    @Getter
    @Setter
    private String firstName;
    @Getter
    @Setter
    private String secondName;
    @Getter
    @Setter
    private String middleName;
    @Getter
    @Setter
    private Integer groupId;
    @Getter
    @Setter
    @Transient
    private Group group;
    @Getter
    @Setter
    private int enabled;

    public User() {
        this.permissionGroup = "ROLE_STUDENT";
        this.enabled = 1;
    }

    public User(String login, String hashPassword, String firstName, String secondName, String middleName) {
        this.login = login;
        this.hashPassword = hashPassword;
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.permissionGroup = "ROLE_STUDENT";
        this.enabled = 1;
    }

    public User(String firstName, String secondName, String middleName, Integer groupId) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.groupId = groupId;
    }
}
