package ru.kata.spring.boot_security.demo.Services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    User findUserByEmail(String email);

    User findUserById(Long id);

    void deleteUser(Long id);

    void saveUser(User user);

    void updateUser(User updateUser, Long id);

    List<User> getAllUser();
}
