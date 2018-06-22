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
    private long id;
    @Getter
    @Setter
    private String homeWorkURL;
    @Getter
    @Setter
    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId", nullable = false)
    private User student;
    @Getter
    @Setter
    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "lessonId", nullable = false)
    private Lessons lesson;
    @Getter
    @Setter
    @Transient
    //TODO need to delete
    private long studentId;
    @Getter
    @Setter
    @Transient
    //TODO need to delete
    private long lessonId;

    public HomeWork(String homeWorkURL, long studentId, long lessonId) {
        this.homeWorkURL = homeWorkURL;
        this.studentId = studentId;
        this.lessonId = lessonId;
    }
}
