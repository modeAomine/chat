package com.example.demo.Controller;

import lombok.AllArgsConstructor;
import com.example.demo.Model.TypingStatusMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class TypingStatusController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/status")
    public void updateTypingStatus(TypingStatusMessage statusMessage) {
        simpMessagingTemplate.convertAndSend("/topic/status", statusMessage);
    }
}
