package ru.innopolis.stc9.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Group {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private String name;

    public Group(String name) {
        this.name = name;
    }
}
