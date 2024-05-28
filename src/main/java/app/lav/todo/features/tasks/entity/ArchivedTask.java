package app.lav.todo.features.tasks.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "archived_tasks")
@NoArgsConstructor
public class ArchivedTask {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String description;

    private Status status;
    @Column(name = "startTime")
    private LocalDateTime startTime;
    @Column(name = "endTime")
    private LocalDateTime endTime;
    private long mainUserId;

    public ArchivedTask(String title, String description, Status status, LocalDateTime startTime, LocalDateTime endTime, long mainUserId) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.mainUserId = mainUserId;
    }
}
