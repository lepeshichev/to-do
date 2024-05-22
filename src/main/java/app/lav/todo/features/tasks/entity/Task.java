package app.lav.todo.features.tasks.entity;

import app.lav.todo.features.auth.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String description;

    private Status status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long mainUserId;
    @ManyToMany
    private HashSet<User> employees;

    public Task(String title, String description, Status status, LocalDateTime startTime, LocalDateTime endTime, long mainUserId) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mainUserId = mainUserId;
    }
}
