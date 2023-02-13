package project.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.todolist.model.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
}
