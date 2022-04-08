package ru.msu.cmc.webprac.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partition_name", insertable = false, updatable = false)
    @NonNull
    private ForumPartition partition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private ForumUser created_by;

    @Column(updatable = false, name = "created_at")
    @CreationTimestamp
    private LocalDateTime created_at;

    @ManyToMany(mappedBy = "threads")
    private Set<ForumUser> users = new HashSet<>();

    @OneToMany(mappedBy = "thread", fetch=FetchType.LAZY)
    private Set<ForumMessage> messages;
}
