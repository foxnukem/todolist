package project.todolist.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import project.todolist.model.Role;
import project.todolist.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    User create(User user);

    User update(User user);

    User readById(long id);

    void delete(long id);

    List<User> getAll();

    Role getRoleByName(String name);

    List<Role> getAllRoles();
}
