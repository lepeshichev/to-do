package app.lav.todo.features.tasks.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Entity
@Table(name = "archive")
@Data
@NoArgsConstructor
public class Archive {
    @Id
    private Long id;
    @OneToMany
    private ArrayList<Task> tasks;
    private long userId;

    public void addTask(Task task) {
        tasks.add(task);
    }

}
