package ru.msu.cmc.webprac.models;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "forum_partition")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ForumPartition {
    @EmbeddedId
    private ForumPartitionId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "created_by")
    private ForumUser created_by;

    @Column(nullable = false, name = "created_at")
    @NonNull
    private Date created_at;

    @Column(nullable = false, name = "general_access")
    @NonNull
    private Boolean general_access;
}
