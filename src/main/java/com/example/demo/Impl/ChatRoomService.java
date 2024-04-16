package com.example.demo.Impl;

import com.example.demo.Model.ChatRoom;
import com.example.demo.Repo.ChatRoomRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepo chatRoomRepo;

    public String generateChatId(String senderId, String recipientId) {
        String chatId;
        if (senderId.compareTo(recipientId) < 0) {
            chatId = senderId + "_" + recipientId;
        } else {
            chatId = recipientId + "_" + senderId;
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(chatId.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


    public ChatRoom findOrCreateChatRoom(String senderId, String recipientId) {
        String chatId = generateChatId(senderId, recipientId);
        ChatRoom chatRoom = chatRoomRepo.findByChatIdIgnoreCase(chatId);
        if (chatRoom == null) {
            chatId = generateChatId(recipientId, senderId);
            chatRoom = chatRoomRepo.findByChatIdIgnoreCase(chatId);
        }
        if (chatRoom != null) {
            return chatRoom;
        } else {
            chatRoom = ChatRoom.builder()
                    .chatId(chatId)
                    .senderId(senderId)
                    .recipientId(recipientId)
                    .build();
            return chatRoomRepo.save(chatRoom);
        }
    }
}