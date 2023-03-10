package project.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.todolist.model.ToDo;

import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    @Query(value = "select id, title, created_at, description, owner_id from todos where owner_id = ?1 union " +
                   "select id, title, created_at, description, owner_id from todos inner join todos_collaborators on id = todo_id and " +
                   "collaborator_id = ?1", nativeQuery = true)
    List<ToDo> getToDosByUserId(long userId);
}
