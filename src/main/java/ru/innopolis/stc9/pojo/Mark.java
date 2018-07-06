package ru.innopolis.stc9.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Mark {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private int value;
    @Getter
    @Setter
    private String comment;
    @Getter
    @Setter
    private User student;
    @Getter
    @Setter
    private Lessons lesson;

    public Mark(int id, int value, User student, Lessons lesson, String comment) {
        this.id = id;
        this.value = value;
        this.comment = comment;
        this.student = student;
        this.lesson = lesson;
    }
}
