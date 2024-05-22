package app.lav.todo.features.auth.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(name = "ismanager")
    private boolean isManager = false;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
