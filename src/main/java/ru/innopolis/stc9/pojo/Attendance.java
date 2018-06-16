package ru.innopolis.stc9.pojo;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class Attendance {
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
