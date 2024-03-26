package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "chat_message")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    private Long id;
    @Column(name = "chat_room_id")
    private String chatRoomId;
    private String senderId;
    private String recipientId;
    private String text;
    private Date timestamp;
    @CollectionTable(name = "message_status", joinColumns = @JoinColumn(name = "chat_message_id"))
    @ElementCollection(targetClass = MessageStatus.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<MessageStatus> messageStatuses = new HashSet<>();
}
