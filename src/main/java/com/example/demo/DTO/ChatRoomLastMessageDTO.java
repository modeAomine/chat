package com.example.demo.DTO;

import com.example.demo.Model.ChatMessage;

public class ChatRoomLastMessageDTO {
    private String chatRoomId;
    private ChatMessage lastMessage;

    public String getChatRoomId() {
        return chatRoomId;
    }

    public ChatMessage getLastMessage() {
        return lastMessage;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public void setLastMessage(ChatMessage lastMessage) {
        this.lastMessage = lastMessage;
    }
}