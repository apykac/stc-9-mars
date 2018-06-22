package ru.innopolis.stc9.pojo;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
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
    private List<Subject> subjects;
    @Getter
    @Setter
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    public Group(String name) {
        this.name = name;
    }
}
