package project.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.todolist.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
