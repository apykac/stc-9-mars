package ru.innopolis.stc9.pojo;

import lombok.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Table(name = "homework")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@XmlRootElement
public class HomeWork implements Serializable {
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

    public HomeWork(String homeWorkURL, User student, Lessons lesson) {
        this.homeWorkURL = homeWorkURL;
        this.student = student;
        this.lesson = lesson;
    }
}
