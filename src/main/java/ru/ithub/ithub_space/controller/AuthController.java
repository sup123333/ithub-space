package ru.ithub.ithub_space.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.ithub.ithub_space.config.JwtService;
import ru.ithub.ithub_space.model.Role;
import ru.ithub.ithub_space.model.User;
import ru.ithub.ithub_space.service.UserService;

@Slf4j
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
        log.info("Попытка входа: {}", request.email());
        User user = userService.findByEmail(request.email());
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            log.warn("Неверный пароль для: {}", request.email());
            return ResponseEntity.status(401).body("Неверный пароль");
        }
        log.info("Успешный вход: {}", request.email());
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
