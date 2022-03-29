package ru.msu.cmc.webprac.models;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "thread")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Thread {
    @EmbeddedId
    private ThreadId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("partition_id")
    @NonNull
    private ForumPartition partition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "created_by")
    private ForumUser created_by;

    @Column(nullable = false, name = "created_at")
    @NonNull
    private Date created_at;

    @ManyToMany(mappedBy = "threads")
    private Set<ForumUser> users = new HashSet<>();
}
