package ru.innopolis.stc9.pojo;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class HomeWork {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private String homeWorkURL;
    @Getter
    @Setter
    private User student;
    @Getter
    @Setter
    private Lessons lesson;

    public HomeWork(String homeWorkURL, User student, Lessons lesson) {
        this.homeWorkURL = homeWorkURL;
        this.student = student;
        this.lesson = lesson;
    }
}
