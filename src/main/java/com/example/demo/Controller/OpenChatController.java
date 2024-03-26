package com.example.demo.Controller;

import com.example.demo.Component.CurrentUserUtil;
import com.example.demo.Impl.ChatMessageService;
import com.example.demo.Impl.ChatRoomService;
import com.example.demo.Impl.UserService;
import com.example.demo.Model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class OpenChatController {

    private final UserService userService;
    private final CurrentUserUtil currentUserUtil;

    @GetMapping("/chat")
    public String chat(Model model) {
        userService.authUser();
        String currentUsername = currentUserUtil.getCurrentUsername();
        User user = userService.findByUsername(currentUsername);
        model.addAttribute("user", user);
        return "html/chat";
    }
}
