package com.example.demo.Controller;

import com.example.demo.Impl.ChatRoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/generate-chat-id")
@AllArgsConstructor
public class ChatRoomController {


    private final ChatRoomService chatRoomService;

    @PostMapping
    public ResponseEntity<Map<String, String>> generateChatId(@RequestBody Map<String, String> payload) {
        String senderId = payload.get("senderId");
        String recipientId = payload.get("recipientId");

        String chatId = chatRoomService.generateChatId(senderId, recipientId);

        Map<String, String> response = new HashMap<>();
        response.put("chatId", chatId);

        return ResponseEntity.ok(response);
    }
}
