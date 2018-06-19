package ru.innopolis.stc9.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "studygroup")
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    @Getter
    @Setter
    @Id
    @SequenceGenerator(name = "studygroupSeq", sequenceName = "STUDYGROUP_SEQUENCE", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "studygroupSeq")
    private int id;
    @Getter
    @Setter
    private String name;

    public Group(String name) {
        this.name = name;
    }
}
