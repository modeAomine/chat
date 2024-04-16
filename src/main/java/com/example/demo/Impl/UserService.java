package com.example.demo.Impl;

import com.example.demo.Model.*;
import com.example.demo.Repo.ArticlesRepo;
import com.example.demo.Repo.NewsRepo;
import com.example.demo.Repo.UserRepo;
import com.example.demo.Service.FileStorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {

    private final NewsRepo newsRepo;
    private final UserRepo userRepo;
    private final ArticlesRepo articlesRepo;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;

    public User save(User user, Role roles) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRegistrationDate(user.getRegistrationDate());
        user.setRoles(Collections.singleton(roles));

        return userRepo.save(user);
    }

    public boolean isUserOnline() {
        User user = authUser();
        if (user != null) {
            return isWithinThreshold(user);
        } else {
            return false;
        }
    }

    private boolean isWithinThreshold(User user) {
        int onlineThresholdMinutes = 1;

        if (user.getLastActive() == null) {
            return false;
        }

        long nowMillis = System.currentTimeMillis();
        long lastActiveMillis = user.getLastActive().getTime();
        long timeDifferenceMillis = nowMillis - lastActiveMillis;
        long thresholdMillis = onlineThresholdMinutes * 60 * 1000;

        return timeDifferenceMillis <= thresholdMillis;
    }


    public void newUserAdd(User user, Role roles) {
        User maymay = userRepo.findByUsername(user.getUsername());
        if (maymay != null) {
            throw new IllegalArgumentException("Пользователь с таким логином уже зарегистрирован!");
        }
        user.setRegistrationDate(new Date());
        user.setRoles(Collections.singleton(roles));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @Transactional
    public void addPublishNewsProfile(User user, News news) {
        if (user != null) {
            if (user.getPublishNewsProfile() == null) {
                user.setPublishNewsProfile(new ArrayList<>());
            }
            news.setUserId(user);
            user.getPublishNewsProfile().add(news);
            newsRepo.save(news);

            System.out.println("News added successfully.");
        } else {
            System.out.println("User not found.");
        }
    }

    public void deleteNews(Long userId, Long newsId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        News news = newsRepo.findById(newsId)
                .orElseThrow(() -> new IllegalArgumentException("News not found with id: " + newsId));

        if (news.getUserId().getId().equals(userId) || news.getUserId().equals(user)) {
            newsRepo.delete(news);
        } else {
            throw new IllegalStateException("User is not authorized to delete this news");
        }
    }

    public void updateUser(User user) {
        User searchUserId = userRepo.findById(user.getId())
                        .orElse(null);
        searchUserId.setUsername(user.getUsername());
        searchUserId.setDiscord(user.getDiscord());
        searchUserId.setEmail(user.getEmail());
        searchUserId.setName(user.getName());
        searchUserId.setFilename(user.getFilename());
        searchUserId.setVK(user.getVK());
        userRepo.save(searchUserId);
    }

    public User updateAvatar(Long id, MultipartFile file) {
        User search = userRepo.findById(id)
                .orElse(null);
        if (file != null && !file.isEmpty()) {
            String filename = fileStorageService.storeFile(file);
            String oldFilename = search.getFilename();
            if (oldFilename != null) {
                fileStorageService.deleteFile(oldFilename);
            }
            search.setFilename(filename);
        }
       return userRepo.save(search);
    }

    public void updateProfileBackground(Long id, MultipartFile file) {
        User user = userRepo.findById(id)
                .orElse(null);
        if (user != null && file != null && !file.isEmpty()) {
            String filename = fileStorageService.storeFile(file);
            String oldProfileBackgroundFilename = user.getProfileBackgroundFilename();
            if (oldProfileBackgroundFilename != null) {
                fileStorageService.deleteFile(oldProfileBackgroundFilename);
            }
            user.setProfileBackgroundFilename(filename);
            userRepo.save(user);
        }
    }


    public void delete(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
        userRepo.delete(user);
    }


    public List<User> findAll() {
        System.out.println(userRepo.findAll());
        return userRepo.findAll();
    }


    public User findById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public List<String> findAllUsername() { return userRepo.findAllUsernames(); }

    public Map<String, Long> findAllByUsernameAndId() {
        List<User> users = userRepo.findAll();
        return users.stream()
                .collect(Collectors.toMap(User::getUsername, User::getId));
    }

    public User findByUsername(String username) { return userRepo.findByUsername(username); }


    public User updateUserLogin(Long id, String username) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));
        user.setUsername(username);
        return userRepo.save(user);
    }


    public User updateUserPassword(Long id, String newPassword) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id:" + id));
        user.setPassword(newPassword);
        return userRepo.save(user);
    }


    public User updateConfirmPassword(Long id, String newConfirmPassword) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
        user.setConfirmPassword(passwordEncoder.encode(newConfirmPassword));
        return userRepo.save(user);
    }


    public User updateUserDiscord(Long id, String newDiscord) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id:" + id));
        user.setDiscord(newDiscord);
        return userRepo.save(user);
    }

    public User updateUserEmail(Long id, String newEmail) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id:" + id));
        user.setEmail(newEmail);
        return userRepo.save(user);
    }

    public User updateUserName(Long id, String newName) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id:" + id));
        user.setName(newName);
        return userRepo.save(user);
    }

    public void registerAuthUser(User user) {
        User existingUser = userRepo.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new IllegalArgumentException("Пользователь с таким логином уже зарегистрирован!");
        }
        user.setRegistrationDate(new Date());
        user.setRoles(Collections.singleton(Role.ADMIN));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public User authUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        User user = userRepo.findByUsername(userDetails.getUsername());

        userRepo.save(user);
        return user;
    }
    public List<User> searchUser(String param, String paramTwo) {
        if (param != null && !param.isEmpty()) {
            return userRepo.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(param, paramTwo);
        } else {
            return findAll();
        }
    }

    public User logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        User user = userRepo.findByUsername(userDetails.getUsername());

        user.getStatuses().add(UserStatus.OFFLINE);
        userRepo.save(user);
        return user;
    }

    public List<Object> search(String searchTerm) {

        List<Articles> articlesList = articlesRepo.findByTitleContainingIgnoreCaseOrUser_UsernameOrCategory_Name(searchTerm, searchTerm, searchTerm);

        List<User> userList = userRepo.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrNameContainingIgnoreCaseOrDiscordContainsIgnoreCaseOrVKContainsIgnoreCase(searchTerm, searchTerm, searchTerm, searchTerm, searchTerm);

        return Stream.concat(articlesList.stream(), userList.stream())
                .collect(Collectors.toList());
    }
}