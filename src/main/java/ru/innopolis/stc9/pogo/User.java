package ru.innopolis.stc9.pogo;

/**
 * Created by Семушев on 24.05.2018.
 */
public class User {
    private int id;
    private String firstName;
    private String secondName;
    private String mddleName;
    private int groupId;
    private Group group;

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

    public String getMddleName() {
        return mddleName;
    }

    public void setMddleName(String mddleName) {
        this.mddleName = mddleName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
