package app.lav.todo.features.tasks.service;

import app.lav.todo.features.auth.repository.UserRepository;
import app.lav.todo.features.tasks.entity.Status;
import app.lav.todo.features.tasks.entity.Task;
import app.lav.todo.features.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TaskService {
    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public void addTask(long userId, String title, String description, String status, LocalDateTime startTime, LocalDateTime endTime) {
        if (userRepository.findById(userId) == null)
            throw new RuntimeException("Такого пользователя не существует");
        else if (title.isEmpty())
            throw new RuntimeException("Заголовок должен быть не пустым");
        else if (description.isEmpty())
            throw new RuntimeException("Описание должно быть не пустым");
        else if (status == null || status.isEmpty())
            throw new RuntimeException("Вы не установили статус");
        else if (startTime.isAfter(endTime))
            throw new RuntimeException("Время начала должно не превышать время конца");
        else if (startTime.isEqual(endTime))
            throw new RuntimeException("Время начала не должно равняться времени конца");
        else
            taskRepository.save(new Task(title, description,startTime,endTime,userId));
    }

    public void deleteTaskById(long id) {
        if (taskRepository.existsById(id))
            taskRepository.deleteById(id);
        else
            throw new RuntimeException("Не найдено такой задачи");
    }

    public void updateTaskById(long id, String title, String description,String status, LocalDateTime startTime, LocalDateTime endTime) {
        Task task;
        if (!taskRepository.existsById(id))
            throw new RuntimeException("Такой задачи не существует");
        else {
            task = taskRepository.findById(id);
        }
        if (title != null) {
            if (title.isEmpty())
                throw new RuntimeException("Заголовок не может быть пустым");
            else
                task.setTitle(title);
        }
        if (description != null) {
            if (description.isEmpty())
                throw new RuntimeException("Описание должно быть не пустым");
            else
                task.setDescription(description);
        }
        if (status != null) {
            if (status.isEmpty())
                throw new RuntimeException("Статус должно быть не пустым");
            else
                task.setStatus(Status.valueOf(status));
        }
        if (startTime != null && endTime == null) {
            if (startTime.isAfter(task.getEndTime()))
                throw new RuntimeException("Время начала должно не превышать время конца");
            else if (startTime.isEqual(task.getEndTime()))
                throw new RuntimeException("Время начала не должно равняться времени конца");
            else {
                task.setStartTime(startTime);
            }
        }
        if (startTime == null && endTime != null) {
            if (task.getStartTime().isAfter(endTime))
                throw new RuntimeException("Время начала должно не превышать время конца");
            else if (task.getStartTime().isEqual(endTime))
                throw new RuntimeException("Время начала не должно равняться времени конца");
            else {
                task.setEndTime(endTime);
            }
        }
        if (startTime != null && endTime != null) {
            if (startTime.isAfter(endTime))
                throw new RuntimeException("Время начала должно не превышать время конца");
            else if (startTime.isEqual(endTime))
                throw new RuntimeException("Время начала не должно равняться времени конца");
            else {
                task.setStartTime(startTime);
                task.setEndTime(endTime);
            }

        }
        taskRepository.save(task);
    }

    public ArrayList<Task> getUserTasks(long id) {
        if (userRepository.findById(id) == null)
            throw new RuntimeException("Такой пользователь не существует");
        else {
            return taskRepository.findAllByMainUserId(id);
        }
    }

}
