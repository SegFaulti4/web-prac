package ru.msu.cmc.webprac.models;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "forum_user")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ForumUser {
    @EmbeddedId
    private ForumUserId id;

    @Column(name = "passwd")
    @NonNull
    private String passwd;

    @Column(name = "status")
    private String status;

    @Column(name = "userrole")
    @NonNull
    private UserRole userrole;

    @Column(nullable = false, name = "created_at")
    @NonNull
    private Date created_at;

    @ManyToMany
    @JoinTable(
            name = "activity",
            joinColumns = { @JoinColumn(name = "username") },
            inverseJoinColumns = { @JoinColumn(name = "thread_name"), @JoinColumn(name = "partition_name") }
    )
    private Set<Thread> threads = new HashSet<>();

    public enum UserRole {
        BANNED("banned"),
        COMMON("common"),
        MODERATOR("moderator");

        private final String name;

        UserRole(String name) {
            this.name = name;
        }

        public static UserRole forString(String str) {
            for (UserRole value : UserRole.values())
                if (value.toString().equals(str))
                    return value;
            throw new IllegalArgumentException();
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
