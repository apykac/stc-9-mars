package ru.innopolis.stc9.pojo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Table(name = "message")
@EqualsAndHashCode
@NoArgsConstructor
@XmlRootElement
public class Message implements Serializable {
    @Getter
    @Setter
    @Id
    @SequenceGenerator(name = "messageSeq", sequenceName = "MESSAGE_SEQUENCE", allocationSize = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messageSeq")
    private int id;
    @Getter
    @Setter
    private String text;
    @Getter
    @Setter
    private String toUserGroup;
    @Getter
    @Setter
    private String uname;
    @Getter
    @Setter
    private String theme;
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toUserId", nullable = true)
    private User toUser;
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fromUserId", nullable = true)
    private User fromUser;
}
