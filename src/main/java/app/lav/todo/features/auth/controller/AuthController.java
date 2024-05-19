package app.lav.todo.features.auth.controller;

import app.lav.todo.features.auth.data.request.AuthRequestData;
import app.lav.todo.features.auth.service.UserService;
import app.lav.todo.global.UserData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody AuthRequestData authRequestData) {
        try {
            if (UserData.IsLogged())
                throw new RuntimeException("Выйдите, чтобы зарегистрироваться");
            userService.signUp(authRequestData.getUsername(), authRequestData.getPassword());
            log.info("Успешная регистрация пользователя");
            return ResponseEntity.ok().body("success signUp");
        } catch (Exception e) {
            log.info("Регистрация пользователя прошла с ошибками");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody AuthRequestData authRequestData) {
        try {
            if (UserData.IsLogged())
                throw new RuntimeException("Выйдите, чтобы войти");
            userService.signIn(authRequestData.getUsername(), authRequestData.getPassword());
            log.info("Успешная авторизация пользователя");
            return ResponseEntity.ok().body("success signIn");
        } catch (Exception e) {
            log.info("Авторизация пользователя прошла с ошибками");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/info")
    public ResponseEntity<String> getInfo() {
        try {
            String result = userService.getUserInfo();
            log.info("Получена информация о пользователе");
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            log.info("Не удалось получить информацию о пользователе");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/sighOut")
    public ResponseEntity<String> signOut() {
        try {
            userService.signOut();
            log.info("Успешный выход");
            return ResponseEntity.ok().body("success signOut");
        } catch (Exception e) {
            log.info("Не удалось выйти");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
