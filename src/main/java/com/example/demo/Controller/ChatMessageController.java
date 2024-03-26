package com.example.demo.Controller;

import com.example.demo.Impl.ChatMessageService;
import com.example.demo.Model.ChatMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/list/")
@AllArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @GetMapping("/{chatRoomId}")
    public ResponseEntity<List<ChatMessage>> getChatMessage(@PathVariable String chatRoomId) {
        List<ChatMessage> messages = chatMessageService.getChatMessagesByChatRoomId(chatRoomId);
        return ResponseEntity.ok(messages);
    }
}
