package app.lav.todo.features.auth.service;

import app.lav.todo.features.auth.entity.User;
import app.lav.todo.features.auth.repository.UserRepository;
import app.lav.todo.global.UserData;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signUp(String username, String password) {
        if (!username.isEmpty() && password.length() >= 3) {
            if (userRepository.findByUsername(username) != null)
                throw new RuntimeException("пользователь с таким логином уже существует");
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            User user = new User(username, hashed);
            UserData.setData(user);
            return userRepository.save(user);
        }
        else {
            throw new RuntimeException("ошибка при создании пользователя - логин пустой или пароль слишком короткий");
        }
    }

    public void signIn(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (BCrypt.checkpw(password,user.getPassword())) {
                UserData.setData(user);
            }
            else {
                throw new RuntimeException("Пароль неверный");
            }
        } else {
            throw new RuntimeException("Пользователя с данным логином не существует");
        }
    }

    public void signOut() {
        if(!UserData.IsLogged())
            throw new RuntimeException("Вы не вошли в аккаунт");
        else {
            UserData.sighOut();
        }
    }
    public String getUserInfo() {
        if (!UserData.IsLogged())
            throw new RuntimeException("Войдите, чтобы получить информацию");
        else
            return "Ваш id " + UserData.getId() + "\nВаш логин " + UserData.getUsername() + "\nВы менеджер? " + UserData.isIsManager();
    }
}
