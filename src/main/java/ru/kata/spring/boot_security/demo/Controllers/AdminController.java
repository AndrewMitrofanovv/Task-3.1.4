package ru.kata.spring.boot_security.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.Services.RoleService;
import ru.kata.spring.boot_security.demo.Services.UserService;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping()
    public String showAdmin(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("newUser", new User());
        model.addAttribute("allUsers", userService.getAllUser());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin";
    }



    @PostMapping("/update")
    public String saveEditedUser(@ModelAttribute User updateUser, @RequestParam("roles") Set<Role> roles) {
        updateUser.setUserRoles(roles);
        userService.updateUser(updateUser, updateUser.getId());
        return "redirect:/admin";
    }


    @PostMapping("/add-user")
    public String addNewUser(@ModelAttribute("newUser") User newUser, @RequestParam("roles") Set<Role> roles) {
        newUser.setUserRoles(roles);
        userService.saveUser(newUser);
        return "redirect:/admin";
    }

    @RequestMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
