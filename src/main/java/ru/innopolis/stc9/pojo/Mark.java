package ru.innopolis.stc9.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Mark implements DBObject {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private int value;
    @Getter
    @Setter
    private int userId;
    @Getter
    @Setter
    private int lessonId;
    @Getter
    @Setter
    private String comment;
}
