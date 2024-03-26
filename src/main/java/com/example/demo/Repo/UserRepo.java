package com.example.demo.Repo;

import com.example.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);

    @Query("SELECT u.username FROM User u")
    List<String> findAllUsernames();

    User findByUsername(String username);

    List<User> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase (String username, String email);

    List<User> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrNameContainingIgnoreCaseOrDiscordContainsIgnoreCase (String Username, String email, String name, String discord);

    List<User> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrNameContainingIgnoreCaseOrDiscordContainsIgnoreCaseOrVKContainsIgnoreCase(String username, String email, String name, String discord, String VK);
}
