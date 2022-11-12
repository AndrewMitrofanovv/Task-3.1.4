package ru.kata.spring.boot_security.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.Services.RoleService;
import ru.kata.spring.boot_security.demo.Services.UserService;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void createTestAdmin() {
        Optional<User> admin = Optional.ofNullable(userService.findUserByUsername("testadmin"));
        if (admin.isEmpty()) {
            User testAdmin = new User("testadmin", "testadmin");
            testAdmin.setUserRoles(roleService.findAllRole());
            userService.saveUser(testAdmin);
        }
    }



    @GetMapping
    public String showAdmin(Model model) {
        User user1 = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", userService.findUserByUsername(user1.getUsername()));
        return "admin";
    }



    @GetMapping("/all-users")
    public ModelAndView allUsers() {
        ModelAndView modelAndView = new ModelAndView("all-users");

        List<User> userList = userService.getAllUser();
        modelAndView.addObject("userList", userList);

        return modelAndView;
    }



    @GetMapping("/{id}")
    public String userInfo(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("roles", roleService.findAllRole());
        return "edit-user";
    }



    @PostMapping("/update/{id}")
    public String saveEditedUser(@ModelAttribute("user")User user, @RequestParam("roles") Set<Role> roles, @PathVariable("id") Long id) {

        user.setUserRoles(roles);
        userService.updateUser(user, id);

        return "redirect:/admin/all-users";
    }



    @GetMapping("/add-user")
    public String addNewUser(@ModelAttribute("user") User user, Model modelUser) {
        modelUser.addAttribute("roles", roleService.findAllRole());
        return "form-user";
    }



    @PostMapping("/save-user")
    public String saveNewUser(@ModelAttribute("user") User user, @RequestParam("roles") Set<Role> roles) {
        user.setUserRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin/all-users";
    }



    @RequestMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/all-users";
    }
}
