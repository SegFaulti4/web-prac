package ru.msu.cmc.webprac.models;

import lombok.*;
import lombok.NonNull;
import org.hibernate.HibernateException;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "forum_user")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ForumUser {
    public ForumUser(@NonNull String name, @NonNull String password, String sts, @NonNull UserRole role) {
        id = name;
        passwd = password;
        status = sts;
        userrole = role;
    }

    @Id
    @Column(name = "username")
    private String id;

    @Column(name = "passwd")
    @NonNull
    private String passwd;

    @Column(name = "status")
    private String status;

    @Column(name = "userrole")
    @Enumerated(EnumType.STRING)
    @NonNull
    private UserRole userrole;

    @Column(name = "created_at", updatable=false)
    @CreationTimestamp
    private LocalDateTime created_at;

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
