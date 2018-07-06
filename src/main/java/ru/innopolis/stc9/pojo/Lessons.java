package ru.innopolis.stc9.pojo;

import lombok.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "lessons")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@XmlRootElement
public class Lessons implements Serializable {
    @Getter
    @Setter
    @Id
    @SequenceGenerator(name = "lessonSeq", sequenceName = "LESSON_SEQUENCE", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lessonSeq")
    private int id;
    @Getter
    @Setter
    private Date date;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subjectId", nullable = false)
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
