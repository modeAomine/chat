package com.example.demo.Repo;

import com.example.demo.Model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ChatMessageRepo extends JpaRepository<ChatMessage, Long> {

    @Query("SELECT m FROM ChatMessage m WHERE m.chatRoomId = :chatRoomId AND LOWER(m.text) LIKE LOWER(CONCAT('%', :text, '%'))")
    List<ChatMessage> findByChatRoomIdAndTextContainingIgnoreCase(@Param("chatRoomId") String chatRoomId, @Param("text") String text);

    List<ChatMessage> findByChatRoomIdOrderByTimestamp(String chatRoomId);

    List<ChatMessage> findByChatRoomIdAndTextContainingIgnoreCaseOrderByTimestamp(String chatRoomId, String text);
}


