package com.example.demo.Repo;

import com.example.demo.Model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipRepo extends JpaRepository<Friendship, Long> {
    boolean existsByUser1Id_IdAndUser2Id_Id(Long user1Id, Long user2Id);

    List<Friendship> findAllByUser1Id_IdOrUser2Id_Id(Long user1Id, Long user2Id);
}
