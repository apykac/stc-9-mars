package ru.innopolis.stc9.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "subject")
@NoArgsConstructor
public class Subject {
    @Getter
    @Setter
    @Id
    @SequenceGenerator(name = "subjectSeq", sequenceName = "SUBJECT_SEQUENCE", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjectSeq")
    private int id;
    @Getter
    @Setter
    private String name;

    public Subject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Subject(String name) {
        this.name = name;
    }
}
