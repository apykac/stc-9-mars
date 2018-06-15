package ru.innopolis.stc9.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
public class Lessons implements DBObject {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private int subject_id;
    @Getter
    @Setter
    private String sname;
    @Getter
    @Setter
    private Date date;
    @Getter
    @Setter
    private String name;

    public Lessons(int id, int subject_id, Date date, String name) {
        this.id = id;
        this.subject_id = subject_id;
        this.date = date;
        this.name = name;
    }

    public Lessons(int subject_id, Date date, String name) {
        this.subject_id = subject_id;
        this.date = date;
        this.name = name;
    }
}
