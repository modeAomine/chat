package com.example.demo.Impl;

import com.example.demo.Model.Subscription;
import com.example.demo.Model.User;
import com.example.demo.Repo.SubscriptionRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscriptionImpl {

    private final SubscriptionRepo subscriptionRepo;
    private final FriendshipImpl friendship;

    public void addSubUserAndTarget(Long subscriber, Long target) {
        if (subscriber.equals(target)) {
            throw new IllegalArgumentException("Вы не можете подписаться сами на себя");
        }

        if (subscriptionRepo.existsBySubscriber_IdAndTarget_Id(subscriber, target)) {
            friendship.addUserFried(subscriber, target);
            subscriptionRepo.deleteBySubscriber_IdAndTarget_Id(subscriber, target);
            System.out.println(subscriber);
            System.out.println(target);
        } else {
            Subscription subscription = new Subscription();
            User user1 = new User();
            user1.setId(subscriber);
            User user2 = new User();
            user2.setId(target);
            subscription.setSubscriber(user1);
            subscription.setTarget(user2);

            subscriptionRepo.save(subscription);
        }
    }
}

