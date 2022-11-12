package ru.kata.spring.boot_security.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.Repositories.UserRepository;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;



    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }



    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(String.format("User with %s id not found", id), 1));
    }



    public void deleteUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.deleteById(id);
        }
    }



    public void saveUser(User user) {
        User userSave = userRepository.findUserByUsername(user.getUsername());
        if (userSave != null) {
            throw new RuntimeException("User exist");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }



    public void updateUser(User updateUser, Long id) {
        updateUser.setId(id);
        userRepository.save(updateUser);
    }



    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
