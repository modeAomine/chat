package com.example.demo.Repo;

import com.example.demo.Model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepo extends JpaRepository<Subscription, Long> {

    boolean existsBySubscriber_IdAndTarget_Id(Long subscriber, Long target);

    void deleteBySubscriber_IdAndTarget_Id(Long subscriber, Long target);
}
