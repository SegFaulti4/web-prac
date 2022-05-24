package ru.msu.cmc.webprac.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "forum_partition")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ForumPartition {

    public ForumPartition(@NonNull String part_name, @NonNull ForumUser user, @NonNull Boolean ga) {
        id = part_name;
        created_by = user;
        general_access = ga;
    }

    @Id
    @Column(name = "partition_name")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private ForumUser created_by;

    @Column(updatable = false, name = "created_at")
    @CreationTimestamp
    private LocalDateTime created_at;

    @Column(name = "general_access")
    @NonNull
    private Boolean general_access;

    @OneToMany(mappedBy = "partition", fetch = FetchType.LAZY)
    private Set<Thread> threads;
}
