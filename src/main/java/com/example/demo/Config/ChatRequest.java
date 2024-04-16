package com.example.demo.Config;

import lombok.Data;

@Data
public class ChatRequest {
    private String senderId;
    private String recipientId;
}
