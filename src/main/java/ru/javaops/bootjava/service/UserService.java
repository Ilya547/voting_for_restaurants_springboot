package ru.javaops.bootjava.service;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.bootjava.model.User;
import ru.javaops.bootjava.repository.UserRepository;

import java.util.List;

import static ru.javaops.bootjava.validation.ValidationUtils.checkEntityWasFound;

@Slf4j
@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User create(@NotNull User user) {
        log.info("Create user: {}", user);
        return repository.save(user);
    }

    public void delete(long id) {
        log.info("Delete user with id = {}", id);
        repository.deleteById(id);
    }

    public User findById(long id) {
        log.info("Find user with id = {}", id);
        return checkEntityWasFound(repository.findById(id), id, User.class);
    }

    public List<User> findAllSorted() {
        log.info("Find all users");
        return repository.findAll(Sort.by("email"));
    }

    @Transactional
    public User update(long id, User user) {
        log.info("Update user with id = {}", user.getId());
        User storedUser = checkEntityWasFound(repository.findById(id), id, User.class);
        user.setId(id);
        user.setPassword(storedUser.getPassword()); // do not update the password, it must be updated in a separate way
        user.setRole(storedUser.getRole()); // do not update roles, it must be updated in a separate way
        return repository.save(user);
    }
}
