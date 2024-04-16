package com.example.demo.Controller;

import com.example.demo.Component.CurrentUserUtil;
import com.example.demo.Config.SendMessageRequest;
import com.example.demo.Impl.ChatMessageService;
import com.example.demo.Impl.ChatRoomService;
import com.example.demo.Impl.UserService;
import com.example.demo.Model.ChatMessage;
import com.example.demo.Model.ChatRoom;
import com.example.demo.Model.MessageStatus;
import com.example.demo.Model.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;
    private final ChatRoomService chatRoomService;

    @GetMapping("/chat/users")
    public ResponseEntity<Map<String, Long>> getAllUsernames() {
        Map<String, Long> usernames = userService.findAllByUsernameAndId();
        return ResponseEntity.ok(usernames);
    }

    @MessageMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody SendMessageRequest request) {
        try {
            String senderId = request.getSenderId();
            String recipientId = request.getRecipientId();
            ChatRoom chatRoom = chatRoomService.findOrCreateChatRoom(senderId, recipientId);
            ChatMessage savedMessage = chatMessageService.sendMessage(senderId, recipientId, request.getText(), chatRoom.getChatId());
            messagingTemplate.convertAndSend("/topic/messages/" + chatRoom.getChatId(), savedMessage);
            return ResponseEntity.ok("окококок");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("хуйня давай по новой: " + e.getMessage());
        }
    }

    @SendTo("/topic/messages/{chatRoomId}")
    public List<ChatMessage> getChatMessages(@DestinationVariable String chatRoomId, @RequestParam(required = false) String text) {
        if (text != null && !text.isEmpty()) {
            return chatMessageService.findByChatRoomIdAndTextContainingIgnoreCaseOrderByTimestamp(chatRoomId, text);
        } else {
            return chatMessageService.getChatMessagesByChatRoomId(chatRoomId);
        }
    }

    @PutMapping("/status/{messageId}/{status}")
    public ResponseEntity<Void> updateMessageStatus(@PathVariable Long messageId, @PathVariable MessageStatus status) {
        ChatMessage message = chatMessageService.findChatMessageById(messageId);
        if (message == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}