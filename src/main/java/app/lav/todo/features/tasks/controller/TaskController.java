package app.lav.todo.features.tasks.controller;

import app.lav.todo.features.tasks.data.request.AddTaskRequestData;
import app.lav.todo.features.tasks.data.request.UpdateTaskRequestData;
import app.lav.todo.features.tasks.service.TaskService;
import app.lav.todo.global.UserData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/addTask")
    public ResponseEntity<String> addTask(@RequestBody AddTaskRequestData addTaskRequestData) {
        try {
            taskService.addTask(UserData.getId(), addTaskRequestData.getTitle(), addTaskRequestData.getDescription(),
                    addTaskRequestData.getStatus(), addTaskRequestData.getStartTime(), addTaskRequestData.getEndTime());
            // todo Добавить логгер
            return ResponseEntity.ok().body("успешное добавление задачи");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteTask/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable long taskId) {
        try {
            taskService.deleteTaskById(taskId);
            return ResponseEntity.ok().body("Успешное удаление задачи");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/updateTask/{taskId}")
    public ResponseEntity<String> updateTask(@RequestBody UpdateTaskRequestData updateTaskRequestData, @PathVariable long taskId) {
        try {
            taskService.updateTaskById(taskId, updateTaskRequestData.getTitle(), updateTaskRequestData.getDescription(),
                    updateTaskRequestData.getStatus(), updateTaskRequestData.getStartTime(), updateTaskRequestData.getEndTime());
            return ResponseEntity.ok().body("Успешное добавление задачи");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/tasks/{userId}")
    public ResponseEntity<String> getAllTasks(@PathVariable long userId) {
        try {
            taskService.getUserTasks(userId);
            return ResponseEntity.ok().body("Успешное получение списка задач пользователя");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}