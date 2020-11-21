package springbootapp.controller;

import springbootapp.model.User;
import springbootapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/main")
public class PagesRestController {

    @Autowired

    private UserService userService;

    @GetMapping("/user")
    public User getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    @GetMapping("/admin")
    public ResponseEntity<List<User>> getAllUsers() {
        final List<User> allUsers = userService.getAllUsers();
        return allUsers != null && !allUsers.isEmpty()
                ? new ResponseEntity<>(allUsers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/admin/{id}")
    public User selectUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/admin")
    public void addNewUser(@RequestParam("firstName") String firstName,
                           @RequestParam("lastName") String lastName,
                           @RequestParam("age") Byte age,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam("role") String role) {
        userService.createUser(firstName, lastName, age, email, password, role);
    }

    @PostMapping("/admin/edit")
    public void editUser(@RequestParam("id") Long id,
                         @RequestParam("firstName") String firstName,
                         @RequestParam("lastName") String lastName,
                         @RequestParam("age") Byte age,
                         @RequestParam("email") String email,
                         @RequestParam("password") String password,
                         @RequestParam("role") String role) {
        userService.editUser(id, firstName, lastName, age, email, password, role);
    }

    @DeleteMapping("/admin/delete/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }
}
