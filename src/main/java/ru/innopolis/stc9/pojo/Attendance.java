package ru.innopolis.stc9.pojo;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@IdClass(AttendancePK.class)
@Table(name = "attendance")
@NoArgsConstructor
//@AllArgsConstructor
@EqualsAndHashCode
public class Attendance {
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
