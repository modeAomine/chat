package com.example.demo.Service;

import com.example.demo.Model.User;
import com.example.demo.Repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class CustomUserService implements UserDetailsService {

    public final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(),
                    user.getRoles()
                            .stream()
                            .map(role -> new SimpleGrantedAuthority( "ROLE_" + role.getName()))
                            .collect(Collectors.toList())
            );
        } else {
            throw new UsernameNotFoundException("Пользователь не найден!");
        }
    }
}