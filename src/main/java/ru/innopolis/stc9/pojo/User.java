package ru.innopolis.stc9.pojo;

/**
 * Created by Семушев on 24.05.2018.
 */
public class User {
    private int id;
    private String firstName;
    private String secondName;
    private String middleName;
    private Integer groupId;
    private Group group;

    public User() {
    }

    public User(String firstName, String secondName, String mddleName, Integer groupId) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = mddleName;
        this.groupId = groupId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
