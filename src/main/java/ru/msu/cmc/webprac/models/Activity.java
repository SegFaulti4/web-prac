package ru.msu.cmc.webprac.models;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "activity")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Activity {

    public Activity(@NonNull String part_name, @NonNull String thd_name, @NonNull String username) {
        id = new ActivityID(part_name, thd_name, username);
    }

    @EmbeddedId
    private ActivityID id;

}
