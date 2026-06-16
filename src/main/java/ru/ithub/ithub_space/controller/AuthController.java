package ru.ithub.ithub_space.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.ithub.ithub_space.config.JwtService;
import ru.ithub.ithub_space.model.Role;
import ru.ithub.ithub_space.model.User;
import ru.ithub.ithub_space.service.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        User user = userService.findByEmail(request.email());
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            return ResponseEntity.status(401).body("Неверный пароль");
        }
        return ResponseEntity.ok(jwtService.generateToken(user.getEmail()));
    }

    record LoginRequest(String email, String password) {}

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
