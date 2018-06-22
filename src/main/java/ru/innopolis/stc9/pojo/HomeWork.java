package ru.innopolis.stc9.pojo;

import lombok.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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
    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId")
    private User student;
    @Getter
    @Setter
    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "lessonId")
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

    public HomeWork(String homeWorkURL, int studentId, int lessonId) {
        this.homeWorkURL = homeWorkURL;
        this.studentId = studentId;
        this.lessonId = lessonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        HomeWork homeWork = (HomeWork) o;

        return new EqualsBuilder()
                .append(getId(), homeWork.getId())
                .append(getStudentId(), homeWork.getStudentId())
                .append(getLessonId(), homeWork.getLessonId())
                .append(getHomeWorkURL(), homeWork.getHomeWorkURL())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getHomeWorkURL())
                .append(getStudentId())
                .append(getLessonId())
                .toHashCode();
    }
}
