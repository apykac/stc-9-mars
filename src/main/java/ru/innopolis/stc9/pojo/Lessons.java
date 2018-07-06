package ru.innopolis.stc9.pojo;

import lombok.*;

import java.sql.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Lessons {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private Date date;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Subject subject;
    @Getter
    @Setter
    private List<Attendance> attendances;
    @Getter
    @Setter
    private List<HomeWork> homeWorks;
    @Getter
    @Setter
    private List<Mark> marks;


    public Lessons(int id, Subject subject, Date date, String name) {
        this.id = id;
        this.subject = subject;
        this.date = date;
        this.name = name;
    }

    public Lessons(Subject subject, Date date, String name) {
        this.subject = subject;
        this.date = date;
        this.name = name;
    }

    public Lessons(int id, Date date, String name) {
        this.id = id;
        this.date = date;
        this.name = name;
    }
}
