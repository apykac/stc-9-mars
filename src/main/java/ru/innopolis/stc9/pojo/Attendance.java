package ru.innopolis.stc9.pojo;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "attendance")
@NoArgsConstructor
@EqualsAndHashCode
public class Attendance {
    @Getter
    @Setter
    @Id
    @SequenceGenerator(name = "attendanceSeq", sequenceName = "ATTENDANCE_SEQUENCE", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attendanceSeq")
    private long id;
    @Getter
    @Setter
    private boolean attended;
    @Getter
    @Setter
    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "lessonId", nullable = false)
    private Lessons lesson;
    @Getter
    @Setter
    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;
    @Getter
    @Setter
    @Transient
    //TODO need to delete
    private long lessonId;
    @Getter
    @Setter
    @Transient
    //TODO need to delete
    private long userId;
}
