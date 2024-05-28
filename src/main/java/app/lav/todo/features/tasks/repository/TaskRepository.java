package app.lav.todo.features.tasks.repository;

import app.lav.todo.features.tasks.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface TaskRepository extends JpaRepository<Task,String> {
    void deleteById(long id);

    boolean existsById(long id);

    Task findById(long id);

    ArrayList<Task> findAllByMainUserId(long userId);
}
