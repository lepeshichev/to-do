package app.lav.todo.features.tasks.service;

import app.lav.todo.features.auth.repository.UserRepository;
import app.lav.todo.features.tasks.entity.ArchivedTask;
import app.lav.todo.features.tasks.entity.Status;
import app.lav.todo.features.tasks.entity.Task;
import app.lav.todo.features.tasks.repository.ArchiveTaskRepository;
import app.lav.todo.features.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    private final ArchiveTaskRepository archiveTaskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, ArchiveTaskRepository archiveTaskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.archiveTaskRepository = archiveTaskRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void addTask(long userId, String title, String description, int statusInt, String startTimeString, String endTimeString) {
        if (startTimeString == null || endTimeString == null)
            throw new RuntimeException("Должно быть указано начальное и конечное время");
        LocalDateTime startTime;
        LocalDateTime endTime;
        try {
            startTime = LocalDateTime.parse(startTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd$HH:mm:ss"));
            endTime = LocalDateTime.parse(endTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd$HH:mm:ss"));
        } catch (Exception e) {
            throw new RuntimeException("Неверный формат даты");
        }
        if (userRepository.findById(userId) == null)
            throw new RuntimeException("Такого пользователя не существует");
        else if (title.isEmpty())
            throw new RuntimeException("Заголовок должен быть не пустым");
        else if (description.isEmpty())
            throw new RuntimeException("Описание должно быть не пустым");
        else if (statusInt < 0 || statusInt > 2)
            throw new RuntimeException("Вы не установили статус");
        else if (startTime.isAfter(endTime))
            throw new RuntimeException("Время начала должно не превышать время конца");
        else if (startTime.isEqual(endTime))
            throw new RuntimeException("Время начала не должно равняться времени конца");
        else {
            Status status;
            switch (statusInt) {
                case 1 -> status = Status.MEDIUM;
                case 2 -> status = Status.HIGH;
                default -> status = Status.LOW;
            }

            taskRepository.save(new Task(title, description, status, startTime, endTime, userId));
        }
    }

    @Transactional
    public void deleteTaskById(long id) {
        if (taskRepository.existsById(id))
            taskRepository.deleteById(id);
        else
            throw new RuntimeException("Не найдено такой задачи");
    }

    @Transactional
    public void updateTaskById(long id, String title, String description, int statusInt, String startTimeString, String endTimeString) {
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
        if (statusInt < 0 || statusInt > 2)
                throw new RuntimeException("Статус имеет недопустимое значение");
            else
                task.setStatus(Status.valueOf(String.valueOf(statusInt))); // Todo Обратить внимание

        if (startTimeString == null || endTimeString == null)
            throw new RuntimeException("Должно быть указано начальное и конечное время");
        LocalDateTime startTime;
        LocalDateTime endTime;
        try {
            startTime = LocalDateTime.parse(startTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd$HH:mm:ss"));
            endTime = LocalDateTime.parse(endTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd$HH:mm:ss"));
        } catch (Exception e) {
            throw new RuntimeException("Неверный формат даты");
        }
        if (startTime.isAfter(task.getEndTime()))
            throw new RuntimeException("Время начала должно не превышать время конца");
        else if (startTime.isEqual(task.getEndTime()))
            throw new RuntimeException("Время начала не должно равняться времени конца");
        else {
            task.setStartTime(startTime);
        }

        if (task.getStartTime().isAfter(endTime))
            throw new RuntimeException("Время начала должно не превышать время конца");
        else if (task.getStartTime().isEqual(endTime))
            throw new RuntimeException("Время начала не должно равняться времени конца");
        else {
            task.setEndTime(endTime);
        }

        if (startTime.isAfter(endTime))
            throw new RuntimeException("Время начала должно не превышать время конца");
        else if (startTime.isEqual(endTime))
            throw new RuntimeException("Время начала не должно равняться времени конца");
        else {
            task.setStartTime(startTime);
            task.setEndTime(endTime);
        }


        taskRepository.save(task);
    }

    public Task getTaskById(long id) {
        if (!taskRepository.existsById(id))
            throw new RuntimeException("Такой задачи не существует");
        else {
            return taskRepository.findById(id);
        }
    }

    public void moveTaskToArchive(long userId, Task task) {
        if (!taskRepository.existsById(task.getId()))
            throw new RuntimeException("Такой задачи не существует");
        else {
            archiveTaskRepository.save(new ArchivedTask(task.getTitle(), task.getDescription(), task.getStatus(),
                    task.getStartTime(), task.getEndTime(), userId));
        }
    }

    public void clearArchive(long id) {
        if (!archiveTaskRepository.existsByMainUserId(id))
            throw new RuntimeException("Такого архива не существует");
        else {
            archiveTaskRepository.deleteAllById(id);
        }
    }

    public ArrayList<Task> getUserTasks(long id) {
        if (userRepository.findById(id) == null)
            throw new RuntimeException("Такой пользователь не существует");
        else {
            return taskRepository.findAllByMainUserId(id);
        }
    }

    public ArrayList<Task> getUserArchivedTasks(long id) {
        if (!archiveTaskRepository.existsByMainUserId(id))
            throw new RuntimeException("Такого архива не существует");
        else {
            return archiveTaskRepository.findAllByMainUserId(id);
        }
    }

}
