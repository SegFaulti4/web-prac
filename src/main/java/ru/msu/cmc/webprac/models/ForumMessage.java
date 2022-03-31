package ru.msu.cmc.webprac.models;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "forum_message")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ForumMessage {
    @Id
    @Column(name = "message_id")
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "thread_name", referencedColumnName = "thread_name"),
            @JoinColumn(name = "partition_name", referencedColumnName = "partition_name")
    })
    @NonNull
    private Thread thread;

    @ManyToOne
    @JoinColumn(name = "reply_to")
    private ForumMessage reply_to;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private ForumUser created_by;

    @Column(name = "message_txt")
    private String message;

    @Column(name = "created_at")
    @NonNull
    private Date created_at;

    @OneToMany(mappedBy = "reply_to")
    private Set<ForumMessage> replies;
}
