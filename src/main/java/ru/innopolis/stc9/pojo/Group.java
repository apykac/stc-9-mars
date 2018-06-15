package ru.innopolis.stc9.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Group implements DBObject {
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
