package ru.innopolis.stc9.pojo;

/**
 * Created by Сергей on 01.06.2018.
 */
public class Education {
    private int id;
    private int groupId;
    private int subjectId;

    public Education(int id, int groupId, int subjectId) {
        this.id = id;
        this.groupId = groupId;
        this.subjectId = subjectId;
    }

    public Education(int groupId, int subjectId) {
        this.groupId = groupId;
        this.subjectId = subjectId;
    }

    public Education() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
}
