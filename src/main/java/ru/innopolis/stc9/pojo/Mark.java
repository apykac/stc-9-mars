package ru.innopolis.stc9.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Table(name = "mark")
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class Mark implements Serializable {
    @Getter
    @Setter
    @Id
    @SequenceGenerator(name = "markSeq", sequenceName = "MARK_SEQUENCE", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "markSeq")
    private int id;
    @Getter
    @Setter
    private int value;
    @Getter
    @Setter
    private String comment;
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId", nullable = false)
    private User student;
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lessonId", nullable = false)
    private Lessons lesson;

    public Mark(int id, int value, User student, Lessons lesson, String comment) {
        this.id = id;
        this.value = value;
        this.comment = comment;
        this.student = student;
        this.lesson = lesson;
    }
}
