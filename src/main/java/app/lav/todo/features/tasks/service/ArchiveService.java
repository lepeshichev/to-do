package app.lav.todo.features.tasks.service;

import app.lav.todo.features.tasks.entity.Archive;
import app.lav.todo.features.tasks.entity.Task;
import app.lav.todo.features.tasks.repository.ArchiveRepository;
import app.lav.todo.features.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class ArchiveService {

    private final TaskRepository taskRepository;

    private final ArchiveRepository archiveRepository;

    @Autowired
    public ArchiveService(TaskRepository taskRepository, ArchiveRepository archiveRepository) {
        this.taskRepository = taskRepository;
        this.archiveRepository = archiveRepository;
    }

    public void moveTaskToArchive(long userId, Task task) {
        if (!taskRepository.existsById(task.getId()))
            throw new RuntimeException("Такой задачи не существует");
        else {
            Archive archive = archiveRepository.findByUserId(userId);
            archive.addTask(task);
            taskRepository.deleteById(task.getId());
        }
    }

    public void clearArchive(long id) {
        if (!archiveRepository.existsById(id))
            throw new RuntimeException("Такого архива не существует");
        else {
            Archive archive = archiveRepository.findById(id);
            archive.setTasks(new ArrayList<>());
            archiveRepository.save(archive);
        }
    }
}
