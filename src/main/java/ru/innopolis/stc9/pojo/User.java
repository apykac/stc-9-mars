package ru.innopolis.stc9.pojo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@EqualsAndHashCode
public class User implements DBObject {
    @Getter
    @Setter
    @Id
    @SequenceGenerator(name = "usersSeq", sequenceName = "USERS_SEQUENCE", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersSeq")
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
    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId")
    private Group group;
    @Getter
    @Setter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messages;
    @Getter
    @Setter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attendance> attendances;
    @Getter
    @Setter
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HomeWork> homeWorks;
    @Getter
    @Setter
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mark> marks;
    @Getter
    @Setter
    @Transient
    //TODO need to delete
    private Integer groupId;

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

    //TODO need to delete
    /*public User(String firstName, String secondName, String middleName, Integer groupId) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.groupId = groupId;
    }*/
    public User(String firstName, String secondName, String middleName, Group group) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.group = group;
    }
}
