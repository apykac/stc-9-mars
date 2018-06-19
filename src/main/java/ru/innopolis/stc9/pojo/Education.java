package ru.innopolis.stc9.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Сергей on 01.06.2018.
 */
@NoArgsConstructor
@AllArgsConstructor
public class Education {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private int groupId;
    @Getter
    @Setter
    private int subjectId;

    public Education(int groupId, int subjectId) {
        this.groupId = groupId;
        this.subjectId = subjectId;
    }
}
