package ru.innopolis.stc9.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "mark")
@NoArgsConstructor
@AllArgsConstructor
public class Mark {
    @Getter
    @Setter
    @Id
    @SequenceGenerator(name = "markSeq", sequenceName = "MARK_SEQUENCE", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "markSeq")
    private int id;

    @Getter
    @Setter
    private int value;

    @Getter
    @Setter
    private int userId;

    @Getter
    @Setter
    private int lessonId;

    @Getter
    @Setter
    private String comment;
}
