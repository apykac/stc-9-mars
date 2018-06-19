package ru.innopolis.stc9.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.sql.Date;

//@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Lessons {
    @Getter
    @Setter
    @Id
    @SequenceGenerator(name = "lessonSeq", sequenceName = "LESSON_SEQUENCE", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lessonSeq")
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
