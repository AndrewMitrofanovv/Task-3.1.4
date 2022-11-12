package ru.kata.spring.boot_security.demo.Services.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.Repositories.UserRepository;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {

    private UserRepository userRepository;


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findUserByUsername(username));
        if (userOptional.isEmpty())
            throw new UsernameNotFoundException(String.format("User with %s username not found", username));
        return userOptional.get();


    }
}
