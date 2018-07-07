package ru.innopolis.stc9.pojo;

import lombok.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "studygroup")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@XmlRootElement
public class Group implements Serializable {
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
    @ManyToMany(fetch = FetchType.EAGER)
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

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
