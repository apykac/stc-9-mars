package ru.innopolis.stc9.pojo;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "lessons")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Lessons {
    @Getter
    @Setter
    @Id
    @SequenceGenerator(name = "lessonSeq", sequenceName = "LESSON_SEQUENCE", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lessonSeq")
    private int id;
    @Getter
    @Setter
    private String sname;
    @Getter
    @Setter
    private Date date;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "subjectId")
    private Subject subject;
    @Getter
    @Setter
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attendance> attendances;
    @Getter
    @Setter
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HomeWork> homeWorks;
    @Getter
    @Setter
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mark> marks;
    @Getter
    @Setter
    @Transient
    //TODO need to delete
    private int subjectId;

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
