package springbootapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springbootapp.model.User;
import springbootapp.service.UserService;

@Controller
public class PagesController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginIn() {
        return "login";
    }

    @RequestMapping("/main")
    public String getUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        model.addAttribute("userNew", new User());
        model.addAttribute("users", userService.getAllUsers());
        return "main";
    }
}
