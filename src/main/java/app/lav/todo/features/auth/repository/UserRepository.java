package app.lav.todo.features.auth.repository;

import app.lav.todo.features.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);

    User findByUsername(String username);

    List<User> findAll();

    boolean deleteById(long id);


}
