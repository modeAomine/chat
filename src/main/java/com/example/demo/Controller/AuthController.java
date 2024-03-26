package com.example.demo.Controller;

import com.example.demo.Impl.UserService;
import com.example.demo.Model.User;
import com.example.demo.Model.UserStatus;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() { return "html/login"; }

    @GetMapping("/logout")
    public String logout() {
        userService.logout();
        return "redirect:/login";
    }
}
