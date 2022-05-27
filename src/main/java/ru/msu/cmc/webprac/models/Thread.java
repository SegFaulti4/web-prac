package ru.msu.cmc.webprac.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "thread")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Thread {

    public Thread(@NonNull String part_name, @NonNull String thd_name, @NonNull ForumUser user) {
        id = new ThreadID(part_name, thd_name);
        created_by = user;
    }

    @EmbeddedId
    private ThreadID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partition_name", insertable = false, updatable = false)
    @NonNull
    private ForumPartition partition;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by")
    private ForumUser created_by;

    @Column(updatable = false, name = "created_at")
    @CreationTimestamp
    private LocalDateTime created_at;

    @Formula(value = "(select count(m.message_id) from forum_message m where m.partition_name = partition_name and m.thread_name = thread_name)")
    private Long msg_count;
}
