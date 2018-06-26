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
@EqualsAndHashCode
public class Attendance {
    /*@Getter
    @Setter
    @Id
    @SequenceGenerator(name = "attendanceSeq", sequenceName = "ATTENDANCE_SEQUENCE", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attendanceSeq")
    private int id;*/
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
}
