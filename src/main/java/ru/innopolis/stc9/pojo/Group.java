package ru.innopolis.stc9.pojo;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "studygroup")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
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
    @Getter
    @Setter
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "education",
            joinColumns = @JoinColumn(name = "groupId"),
            inverseJoinColumns = @JoinColumn(name = "subjectId"))
    private List<Subject> subject;

    public Group(String name) {
        this.name = name;
    }
}
