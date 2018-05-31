package ru.innopolis.stc9.pojo;

/**
 * Created by Семушев on 24.05.2018.
 */
public class User {
    private int id;
    private String login;
    private String hashPassword;
    private String permissionGroup;
    private String firstName;
    private String secondName;
    private String middleName;
    private Integer groupId;
    private Group group;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public String getPermissionGroup() {
        return permissionGroup;
    }

    public void setPermissionGroup(String permissionGroup) {
        this.permissionGroup = permissionGroup;
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

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }
}
