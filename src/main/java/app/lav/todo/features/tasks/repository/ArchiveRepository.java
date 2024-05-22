package app.lav.todo.features.tasks.repository;

import app.lav.todo.features.tasks.entity.Archive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveRepository extends JpaRepository<Archive, String> {
    boolean existsById(long id);
    Archive findById(long id);
    Archive findByUserId(long id);
}
