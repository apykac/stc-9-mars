package ru.innopolis.stc9.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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
    @Getter
    @Setter
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "education",
            joinColumns = @JoinColumn(name = "subjectId"),
            inverseJoinColumns = @JoinColumn(name = "groupId"))
    private List<Group> group;

    public Subject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Subject(String name) {
        this.name = name;
    }
}
