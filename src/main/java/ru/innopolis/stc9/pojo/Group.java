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
    @Column(unique = true)
    private String name;
    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "education",
            joinColumns = @JoinColumn(name = "groupId"),
            inverseJoinColumns = @JoinColumn(name = "subjectId"))
    private List<Subject> subjects;
    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_group",
            joinColumns = @JoinColumn(name = "groupId"),
            inverseJoinColumns = @JoinColumn(name = "userId"))
    private List<User> users = new ArrayList<>();

    public Group(String name) {
        this.name = name;
    }
}
