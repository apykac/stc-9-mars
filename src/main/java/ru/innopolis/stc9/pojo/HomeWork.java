package ru.innopolis.stc9.pojo;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "homework")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class HomeWork {
    @Getter
    @Setter
    @Id
    @SequenceGenerator(name = "homeWorkSeq", sequenceName = "HOMEWORK_SEQUENCE", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "homeWorkSeq")
    private int id;
    @Getter
    @Setter
    private String homeWorkURL;
    @Getter
    @Setter
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId", nullable = false)
    private User student;
    @Getter
    @Setter
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "lessonId", nullable = false)
    private Lessons lesson;
    @Getter
    @Setter
    @Transient
    //TODO need to delete
    private int studentId;
    @Getter
    @Setter
    @Transient
    //TODO need to delete
    private int lessonId;

    public HomeWork(String homeWorkURL, User student, Lessons lesson) {
        this.homeWorkURL = homeWorkURL;
        this.student = student;
        this.lesson = lesson;
    }

    public HomeWork(int id, String homeWorkURL, User student, Lessons lesson) {
        this.id = id;
        this.homeWorkURL = homeWorkURL;
        this.student = student;
        this.lesson = lesson;
    }
}
