package com.example.demo.Impl;

import com.example.demo.DTO.ChatRoomLastMessageDTO;
import com.example.demo.Model.ChatMessage;
import com.example.demo.Repo.ChatMessageRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepo chatMessageRepo;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatMessage sendMessage(String senderId, String recipientId, String text, String chatRoomId) {
        ChatMessage message = ChatMessage.builder()
                .senderId(senderId)
                .recipientId(recipientId)
                .text(text)
                .timestamp(new Date())
                .chatRoomId(chatRoomId)
                .build();
        chatMessageRepo.save(message);
        simpMessagingTemplate.convertAndSendToUser(recipientId, "/queue/messages", message);
        return message;
    }

    public ChatMessage findChatMessageById(Long messageId) {
        Optional<ChatMessage> optionalMessage = chatMessageRepo.findById(messageId);
        return optionalMessage.orElse(null);
    }

    public List<ChatMessage> getChatMessagesByChatRoomId(String chatRoomId) {
        return chatMessageRepo.findByChatRoomIdOrderByTimestamp(chatRoomId);
    }

    public ChatMessage getLastChatMessageByChatRoomId(String chatRoomId) {
        return chatMessageRepo.findFirstByChatRoomIdOrderByTimestampDesc(chatRoomId);
    }

    public List<ChatRoomLastMessageDTO> getAllChatRoomsWithLastMessages() {
        List<String> allChatRoomIds = chatMessageRepo.findAllChatRoomIds();
        List<ChatRoomLastMessageDTO> chatRoomsWithLastMessages = new ArrayList<>();

        for (String chatRoomId : allChatRoomIds) {
            Pageable pageable = PageRequest.of(0, 1);
            List<ChatMessage> lastMessageList = chatMessageRepo.findLastMessageByChatRoomId(chatRoomId, pageable);

            if (!lastMessageList.isEmpty()) {
                ChatMessage lastMessage = lastMessageList.get(0);
                ChatRoomLastMessageDTO chatRoomLastMessageDTO = new ChatRoomLastMessageDTO();
                chatRoomLastMessageDTO.setChatRoomId(chatRoomId);
                chatRoomLastMessageDTO.setLastMessage(lastMessage);
                chatRoomsWithLastMessages.add(chatRoomLastMessageDTO);
            }
        }

        return chatRoomsWithLastMessages;
    }

    public List<ChatMessage> findByChatRoomIdAndTextContainingIgnoreCaseOrderByTimestamp(String chatRoomId, String text) {
        return chatMessageRepo.findByChatRoomIdAndTextContainingIgnoreCaseOrderByTimestamp(chatRoomId, text);
    }
}