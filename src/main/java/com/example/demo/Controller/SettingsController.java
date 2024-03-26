package com.example.demo.Controller;

import com.example.demo.Impl.UserService;
import com.example.demo.Model.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@AllArgsConstructor
@RequestMapping
public class SettingsController {

    private final UserService userService;


    @GetMapping("/settings")
    public String userSettings(Model model) {
        User user = userService.authUser();
        model.addAttribute("user", user);
        return "html/setting";
    }

    @PostMapping("/settings/updateUser")
    public String updateUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/profile";
    }

    @PostMapping("/settings/updateAvatar")
    public ResponseEntity<String> updateAvatar(@RequestParam Long id, @RequestParam MultipartFile filename) {
        try {
            userService.updateAvatar(id, filename);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(ServletUriComponentsBuilder.fromCurrentContextPath().path("/profile").build().toUri())
                    .body("Avatar updated successfully");
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/settings/updateProfileBackground")
    public String updateProfileBackground(@RequestParam Long id, @RequestParam MultipartFile profileBackgroundFilename) {
        try {
            userService.updateProfileBackground(id, profileBackgroundFilename);
            return "redirect:/profile";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }



    @PostMapping("/settings/updateDiscord")
    @ResponseBody
    public ResponseEntity<User> updateDiscord(@RequestBody User user) {
        try {
            Long userId = user.getId();
            String newDiscord = user.getDiscord();
            userService.updateUserDiscord(userId, newDiscord);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PostMapping("/settings/updateLogin")
    @ResponseBody
    public ResponseEntity<User> updateLogin(@RequestBody User user) {
        try {
            Long userId = user.getId();
            String newLogin = user.getUsername();
            User updatedUser = userService.updateUserLogin(userId, newLogin);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PostMapping("/settings/updatePassword")
    @ResponseBody
    public ResponseEntity<User> updatePassword(@RequestBody User user) {
        try {
            Long userId = user.getId();
            String newPassword = user.getPassword();
            String newConfirmPassword = user.getConfirmPassword();
            User updatedUser = userService.updateUserPassword(userId, newPassword);
            User updateUserConfirmPassword = userService.updateConfirmPassword(userId, newConfirmPassword);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PostMapping("/settings/updateEmail")
    @ResponseBody
    public ResponseEntity<User> updateEmail(@RequestBody User user) {
        try {
            Long userId = user.getId();
            String newEmail = user.getEmail();
            User updatedUser = userService.updateUserEmail(userId, newEmail);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
