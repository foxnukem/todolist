package project.todolist.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.todolist.exception.NullReferenceEntityException;
import project.todolist.model.Role;
import project.todolist.model.User;
import project.todolist.repository.RoleRepository;
import project.todolist.repository.UserRepository;
import project.todolist.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.springframework.security.core.userdetails.User.withUsername;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User create(User user) {
        if (user == null) {
            log.error("UserServiceImpl#create: Given User cannot be saved (user=null)");
            throw new NullReferenceEntityException("Given User cannot be null");
        }
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        if (user == null) {
            log.error("UserServiceImpl#create: Given User cannot be saved (user=null)");
            throw new NullReferenceEntityException("Given User cannot be null");
        }
        var oldUser = userRepository.findById(user.getId());
        if (oldUser.isEmpty()) {
            log.error("UserServiceImpl#update: User (id=" + user.getId() + ") was not found");
            throw new EntityNotFoundException("User (id=" + user.getId() + ") was not found");
        }
        return userRepository.save(user);

    }

    @Override
    public User readById(long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            log.error("UserServiceImpl#readById: User (id=" + id + ") was not found");
            throw new EntityNotFoundException("User (id=" + id + ") was not found");
        });
    }

    @Override
    public void delete(long id) {
        userRepository.findById(id)
                .ifPresentOrElse(
                        u -> {
                            log.info("UserServiceImpl#delete: Deleted " + u);
                            userRepository.delete(u);
                        },
                        () -> {
                            log.error("UserServiceImpl#delete: User (id=" + id + ") was not found");
                            throw new EntityNotFoundException("User (id=" + id + ") was not found");
                        });

    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Role getRoleByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Argument name cannot be null or blank");
        }
        String roleName = name.toUpperCase();
        return roleRepository.getByName(roleName).orElseThrow(() -> {
            log.error("UserServiceImpl#getRoleByName: Cannot find Role " + roleName);
            throw new EntityNotFoundException("Cannot find Role " + roleName);
        });
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userByEmail = userRepository.findUserByEmail(username).orElseThrow(() -> {
            log.error("UserServiceImpl#loadUserByUsername: User with username=" + username + " was not found");
            throw new UsernameNotFoundException("User with username=" + username + " was not found");
        });
        org.springframework.security.core.userdetails.User.UserBuilder userBuilder = withUsername(userByEmail.getEmail());
        userBuilder.password(userByEmail.getPassword());
        userBuilder.roles(userByEmail.getRole().getName());
        return userBuilder.build();
    }
}
