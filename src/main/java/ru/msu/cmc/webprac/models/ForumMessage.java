package ru.msu.cmc.webprac.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "forum_message")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ForumMessage {

    public ForumMessage(@NonNull Thread t, ForumMessage replied, @NonNull ForumUser u, @NonNull String msg) {
        thread = t;
        reply_to = replied;
        created_by = u;
        message = msg;
    }

    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "thread_name", referencedColumnName = "thread_name"),
            @JoinColumn(name = "partition_name", referencedColumnName = "partition_name")
    })
    @NonNull
    private Thread thread;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_to")
    private ForumMessage reply_to;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private ForumUser created_by;

    @Column(name = "message_txt")
    private String message;

    @Column(updatable = false, name = "created_at")
    @CreationTimestamp
    private LocalDateTime created_at;

    @OneToMany(mappedBy = "reply_to", fetch = FetchType.LAZY)
    private Set<ForumMessage> replies;
}
