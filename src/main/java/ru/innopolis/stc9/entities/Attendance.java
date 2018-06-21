package ru.innopolis.stc9.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.innopolis.stc9.pojo.Lessons;
import ru.innopolis.stc9.pojo.User;

import javax.persistence.*;

@Entity
@Table(name = "attendance")
@NoArgsConstructor
public class Attendance {

    @Getter
    @Setter
    @Id
    @SequenceGenerator(name = "attendanceSeq", sequenceName = "ATTENDANCE_SEQUENCE", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attendanceSeq")
    private int id;

    @Getter
    @Setter
    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Lessons lesson;

    @Getter
    @Setter
    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    @Getter
    @Setter
    private boolean attended;
}
