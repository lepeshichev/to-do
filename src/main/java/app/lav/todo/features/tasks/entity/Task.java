package app.lav.todo.features.tasks.entity;

import app.lav.todo.features.auth.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    @Column(name = "start_Time")
    private LocalDateTime startTime;
    @Column(name = "end_Time")
    private LocalDateTime endTime;
    @Column(name = "main_user_Id")
    private long mainUserId;

    public Task(String title, String description, Status status, LocalDateTime startTime, LocalDateTime endTime, long mainUserId) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mainUserId = mainUserId;
    }
}
