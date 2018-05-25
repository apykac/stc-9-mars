package ru.innopolis.stc9.pojo;

/**
 * Created by Семушев on 24.05.2018.
 */
public class Login {
    private int id;
    private String userName;
    private long hashPassword;
    private int permissionGroup;
    private Integer userId;
    private User user;

    public Login() {
    }

    public Login(String userName, long hashPassword, Integer userId) {
        this.userName = userName;
        this.hashPassword = hashPassword;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(long hashPassword) {
        this.hashPassword = hashPassword;
    }

    public int getPermissionGroup() {
        return permissionGroup;
    }

    public void setPermissionGroup(int permissionGroup) {
        this.permissionGroup = permissionGroup;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
