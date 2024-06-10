package com.example.demo.Repo;

import com.example.demo.Model.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ChatMessageRepo extends JpaRepository<ChatMessage, Long> {

    @Query("SELECT m FROM ChatMessage m WHERE m.chatRoomId = :chatRoomId AND LOWER(m.text) LIKE LOWER(CONCAT('%', :text, '%'))")
    List<ChatMessage> findByChatRoomIdAndTextContainingIgnoreCase(@Param("chatRoomId") String chatRoomId, @Param("text") String text);

    List<ChatMessage> findByChatRoomIdOrderByTimestamp(String chatRoomId);

    ChatMessage findFirstByChatRoomIdOrderByTimestampDesc(String chatRoomId);

    @Query("SELECT DISTINCT c.chatRoomId FROM ChatMessage c")
    List<String> findAllChatRoomIds();

    @Query("SELECT c FROM ChatMessage c WHERE c.chatRoomId = :chatRoomId ORDER BY c.timestamp DESC")
    List<ChatMessage> findLastMessageByChatRoomId(@Param("chatRoomId") String chatRoomId, Pageable pageable);

    List<ChatMessage> findByChatRoomIdAndTextContainingIgnoreCaseOrderByTimestamp(String chatRoomId, String text);
}