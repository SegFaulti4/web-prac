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
public class ForumUserID implements Serializable {
    @Column(nullable = false, name = "username")
    private String username;
}
