package springbootapp.controller;

import springbootapp.model.User;
import springbootapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminsController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String getAdminAndAllUsers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getAllUsers());
        return "/admin_panel";
    }

    @PostMapping()
    public String addNewUser(@RequestParam("firstName") String firstName,
                             @RequestParam("lastName") String lastName,
                             @RequestParam("age") Byte age,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password,
                             @RequestParam("role") String role) {
        userService.createUser(firstName, lastName, age, email, password, role);
        return "redirect:/admin";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "redirect:/admin";
    }

    @PostMapping("/{id}")
    public String editUser(@PathVariable("id") Long id,
                           @RequestParam("firstName") String firstName,
                           @RequestParam("lastName") String lastName,
                           @RequestParam("age") Byte age,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam("role") String role) {
        userService.updateUser(id, firstName, lastName, age, email, password, role);
        return "redirect:/admin";
    }

    @GetMapping("/remove/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}