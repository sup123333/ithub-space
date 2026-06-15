package ru.ithub.ithub_space.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ithub.ithub_space.model.Role;
import ru.ithub.ithub_space.model.User;
import ru.ithub.ithub_space.service.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        User user = userService.register(
                request.firstName(),
                request.lastName(),
                request.middleName(),
                request.email(),
                request.password(),
                request.city(),
                request.direction(),
                Role.valueOf(request.role())
        );
        return ResponseEntity.ok("Пользователь " + user.getEmail() + " зарегистрирован");
    }

    record RegisterRequest(
            String firstName,
            String lastName,
            String middleName,
            String email,
            String password,
            String city,
            String direction,
            String role
    ) {}
}
