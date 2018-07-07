package ru.innopolis.stc9.pojo;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@IdClass(AttendancePK.class)
@Table(name = "attendance")
@NoArgsConstructor
@EqualsAndHashCode
@XmlRootElement
public class Attendance implements Serializable {
    @Getter
    @Setter
    private boolean attended;
    @Id
    @Getter
    @Setter
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "lessonId", nullable = false)
    private Lessons lesson;
    @Id
    @Getter
    @Setter
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public Attendance(boolean attended, Lessons lesson, User user) {
        this.attended = attended;
        this.lesson = lesson;
        this.user = user;
    }
}
