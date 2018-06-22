package ru.innopolis.stc9.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

//TODO need to delete this class
//@Entity
@Table(name = "education")
@NoArgsConstructor
@AllArgsConstructor
public class Education {
    @Getter
    @Setter
    @Id
    @SequenceGenerator(name = "educationSeq", sequenceName = "EDUCATION_SEQUENCE", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "educationSeq")
    private long id;
    @Getter
    @Setter
    private long groupId;
    @Getter
    @Setter
    private long subjectId;

    public Education(long groupId, long subjectId) {
        this.groupId = groupId;
        this.subjectId = subjectId;
    }
}
