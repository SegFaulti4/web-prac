package ru.msu.cmc.webprac.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForumPartitionId implements Serializable {
    @Column(nullable = false, name = "partition_name")
    private String partition_name;
}
