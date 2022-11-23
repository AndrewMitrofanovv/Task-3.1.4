package ru.kata.spring.boot_security.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kata.spring.boot_security.demo.Repositories.UserRepository;
import ru.kata.spring.boot_security.demo.Services.UserService;
import ru.kata.spring.boot_security.demo.Services.UserServiceImpl;

@SpringBootTest
class SpringBootSecurityDemoApplicationTests {
	@Autowired
	UserServiceImpl userServiceImpl;

	@Test
	void contextLoads() {
		userServiceImpl.getAllUser();
	}

}
