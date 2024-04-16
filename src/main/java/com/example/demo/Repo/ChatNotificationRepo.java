package com.example.demo.Repo;

import com.example.demo.Model.ChatNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatNotificationRepo extends JpaRepository<ChatNotification, Long> {

}
