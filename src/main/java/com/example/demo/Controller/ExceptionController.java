package com.example.demo.Controller;

import com.example.demo.Impl.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/exception")
public class ExceptionController {

    private final UserService userService;

    @GetMapping("/404")
    public String found404() {
        userService.authUser();
        return "exception/404";
    }

    @PostMapping("/404")
    public String redirectHome() {
        return "redirect:/articles";
    }
}
