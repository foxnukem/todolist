package project.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.todolist.model.ToDo;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {
}
