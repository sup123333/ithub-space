package ru.ithub.ithub_space.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.ithub.ithub_space.dto.UserResponse;
import ru.ithub.ithub_space.model.UserEntity;
import ru.ithub.ithub_space.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        UserEntity user = userService.findByEmail(userDetails.getUsername());
        return ResponseEntity.ok(UserResponse.from(user));
    }
}
