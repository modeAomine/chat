package com.example.demo.Impl;

import com.example.demo.Model.Friendship;
import com.example.demo.Model.User;
import com.example.demo.Repo.FriendshipRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class FriendshipImpl {

    private final FriendshipRepo friendshipRepo;
    private final UserService userService;

    public void addUserFried(Long userId1, Long userId2) {
        if (userId1.equals(userId2)) {
            throw new IllegalArgumentException("Нельзя добавить самого себя в друзья");
        }

        if (friendshipRepo.existsByUser1Id_IdAndUser2Id_Id(userId1, userId2) ||
                friendshipRepo.existsByUser1Id_IdAndUser2Id_Id(userId2, userId1)) {
            throw new IllegalArgumentException("Эти пользователи уже являются друзьями");
        }
        Friendship friendship = new Friendship();
        User user1 = new User();
        user1.setId(userId1);
        User user2 = new User();
        user2.setId(userId2);
        friendship.setUser1Id(user1);
        friendship.setUser2Id(user2);
        friendshipRepo.save(friendship);
    }

    public List<User> getFriendsByUsername(String username) {
        Map<String, Long> usernameToIdMap = userService.findAllByUsernameAndId();
        Long userId = usernameToIdMap.get(username);
        if (userId == null) {
            throw new IllegalArgumentException("Пользователь с именем " + username + " не найден");
        }

        List<Friendship> friendships = friendshipRepo.findAllByUser1Id_IdOrUser2Id_Id(userId, userId);
        List<User> friends = new ArrayList<>();

        for (Friendship friendship : friendships) {
            if (friendship.getUser1Id().getId().equals(userId)) {
                friends.add(friendship.getUser2Id());
            } else {
                friends.add(friendship.getUser1Id());
            }
        }

        return friends;
    }
}
