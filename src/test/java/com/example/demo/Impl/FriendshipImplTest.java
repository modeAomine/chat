package com.example.demo.Impl;


import com.example.demo.Model.Friendship;
import com.example.demo.Model.Subscription;
import com.example.demo.Model.User;
import com.example.demo.Repo.FriendshipRepo;
import com.example.demo.Repo.SubscriptionRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FriendshipImplTest {

    @Mock
    private FriendshipRepo friendshipRepo;

    @Mock
    private UserService userService;

    @Mock
    private SubscriptionRepo subscriptionRepo;

    @InjectMocks
    private FriendshipImpl friendshipImpl;

    @Test
    public void testGetFriendsByUsername() {
        Map<String, Long> usernameToIdMap = new HashMap<>();
        usernameToIdMap.put("user1", 1L);
        usernameToIdMap.put("user2", 2L);
        usernameToIdMap.put("user3", 3L);
        usernameToIdMap.put("user4", 4L);
        when(userService.findAllByUsernameAndId()).thenReturn(usernameToIdMap);
        Friendship friendship1 = new Friendship();
        Friendship friendship2 = new Friendship();
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        friendship1.setUser1Id(user1);
        friendship1.setUser2Id(user2);
        friendship2.setUser1Id(user2);
        friendship2.setUser2Id(user1);
        List<Friendship> friendships = Arrays.asList(friendship1, friendship2);
        when(friendshipRepo.findAllByUser1Id_IdOrUser2Id_Id(1L, 1L)).thenReturn(friendships);
        List<User> friends = friendshipImpl.getFriendsByUsername("user1");
        assertEquals(1, friends.size());
        assertEquals(user2, friends.get(0));
    }

    @Test
    public void testAddSubUserAndTarget() {
        when(subscriptionRepo.existsBySubscriber_IdAndTarget_Id(1L, 2L)).thenReturn(false);
        friendshipImpl.addUserFried(1L, 2L);
        verify(subscriptionRepo, times(1)).save(any(Subscription.class));
    }

    @Test
    public void testAddSubUserAndTargetSelfSubscription() {
        assertThrows(IllegalArgumentException.class, () -> {
            friendshipImpl.addUserFried(1L, 1L);
        });
    }
}