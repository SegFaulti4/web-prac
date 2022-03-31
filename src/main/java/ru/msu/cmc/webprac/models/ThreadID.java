package ru.msu.cmc.webprac.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThreadID implements Serializable {
    private ForumPartitionID partition_id;

    @Column(nullable = false, name = "thread_name")
    private String thread_name;
}
