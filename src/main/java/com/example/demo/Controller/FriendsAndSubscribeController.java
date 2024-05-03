package com.example.demo.Controller;

import com.example.demo.Impl.FriendshipImpl;
import com.example.demo.Impl.SubscriptionImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@AllArgsConstructor
@RequestMapping
public class FriendsAndSubscribeController {

    private final SubscriptionImpl subscription;

    @PostMapping("/sub/{subscriber}/to/{target}")
    public String addSubToTarget(@PathVariable Long subscriber, @PathVariable Long target, HttpServletRequest request) {
        if (subscriber != null && target != null) {
            subscription.addSubUserAndTarget(subscriber, target);
            String referer = request.getHeader("referer");
            return "redirect:" + referer;
        } else {
            throw new IllegalArgumentException("Некорректные идентификаторы пользователей");
        }
    }
}
