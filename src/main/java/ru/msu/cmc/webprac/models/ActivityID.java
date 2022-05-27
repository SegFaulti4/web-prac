package ru.msu.cmc.webprac.models;

import lombok.*;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityID implements Serializable {
    @Column(name = "partition_name")
    private String partition_name;

    @Column(name = "thread_name")
    private String thread_name;

    @Column(name = "username")
    private String username;
}
