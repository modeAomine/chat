package com.example.demo.Repo;

import com.example.demo.Model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepo extends JpaRepository<ChatRoom, Long> {

    ChatRoom findByChatIdIgnoreCase(String chatId);

    ChatRoom findBySenderIdAndRecipientId(String senderId, String recipientId);
}