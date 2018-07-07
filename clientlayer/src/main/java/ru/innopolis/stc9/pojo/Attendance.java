package ru.innopolis.stc9.pojo;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@EqualsAndHashCode
public class Attendance {
    @Getter
    @Setter
    private boolean attended;
    @Getter
    @Setter
    private Lessons lesson;
    @Getter
    @Setter
    private User user;

    public Attendance(boolean attended, Lessons lesson, User user) {
        this.attended = attended;
        this.lesson = lesson;
        this.user = user;
    }
}
