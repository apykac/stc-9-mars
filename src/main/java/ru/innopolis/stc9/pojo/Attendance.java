package ru.innopolis.stc9.pojo;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
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
    private int lessonId;
    @Getter
    @Setter
    private int userId;
    @Getter
    @Setter
    private boolean attended;
}
