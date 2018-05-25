package ru.innopolis.stc9.pojo;

/**
 * Created by Семушев on 24.05.2018.
 */
public class Login {
    private int id;
    private String userName;
    private long hashPassword;
    private int premissionGroup;
    private int userId;
    private User user;

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

    public int getPremissionGroup() {
        return premissionGroup;
    }

    public void setPremissionGroup(int premissionGroup) {
        this.premissionGroup = premissionGroup;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
