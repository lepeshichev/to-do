package app.lav.todo.features.tasks.controller;

import app.lav.todo.features.tasks.entity.Task;
import app.lav.todo.features.tasks.service.TaskService;
import app.lav.todo.global.UserData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@Slf4j
@RequestMapping("/archive")
public class ArchiveController {
    private final TaskService taskService;

    @Autowired
    public ArchiveController(TaskService taskService) {
        this.taskService = taskService;
    }

    @DeleteMapping("/moveTaskToArchive/{taskId}")
    public ResponseEntity<String> moveTaskToArchive(@PathVariable long taskId) {
        try {
            taskService.moveTaskToArchive(UserData.getId(), taskService.getTaskById(taskId));
            log.info("перемещаем задачу в архив");
            taskService.deleteTaskById(taskId);
            log.info("Удаляем задачу по айди");
            return ResponseEntity.ok().body("Успешное добавление задачи в архив");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/archivedTasks")
    public ResponseEntity<ArrayList<Task>> getArchivedTasks() {
        try {
            taskService.getUserArchivedTasks(UserData.getId());
            log.info("Получаем все заархивированные задачи пользователя");
            return ResponseEntity.ok().body(taskService.getUserArchivedTasks(UserData.getId()));
        } catch (Exception e) {
            log.info("Ошибка получения заархивированных задач");
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }
    }
}
