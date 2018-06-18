package ru.innopolis.stc9.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
public class Lessons {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private int subjectId;
    @Getter
    @Setter
    private String sname;
    @Getter
    @Setter
    private Date date;
    @Getter
    @Setter
    private String name;

    public Lessons(int id, int subjectId, Date date, String name) {
        this.id = id;
        this.subjectId = subjectId;
        this.date = date;
        this.name = name;
    }

    public Lessons(int subjectId, Date date, String name) {
        this.subjectId = subjectId;
        this.date = date;
        this.name = name;
    }
}
