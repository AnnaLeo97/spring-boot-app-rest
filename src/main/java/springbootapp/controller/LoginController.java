package springbootapp.controller;

import springbootapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String loginIn() {
        return "login";
    }

}
