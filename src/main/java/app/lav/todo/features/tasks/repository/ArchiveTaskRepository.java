package app.lav.todo.features.tasks.repository;

import app.lav.todo.features.tasks.entity.ArchivedTask;
import app.lav.todo.features.tasks.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ArchiveTaskRepository extends JpaRepository<ArchivedTask,String> {
    void deleteAllById(long id);

    boolean existsByMainUserId(long id);

    ArrayList<Task> findAllByMainUserId(long id);

}
