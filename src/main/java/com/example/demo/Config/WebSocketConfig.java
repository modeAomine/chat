package com.example.demo.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Класс конфигурации WebSocket.
 * Указывает настройки для работы с WebSocket, включая настройку точки входа и конфигурацию брокера сообщений.
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    /**
     * Регистрирует точку входа для обработки WebSocket-соединений.
     * Указывает путь к точке входа и разрешает доступ только с указанных источников.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat").setAllowedOrigins("http://89.104.68.199:6066").withSockJS();
    }


    /**
     * Конфигурирует брокер сообщений WebSocket.
     * Включает простой брокер сообщений с указанными префиксами для направления сообщений.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/user", "/topic");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }
}

