package ru.ithub.ithub_space.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ithub.ithub_space.exception.ConflictException;
import ru.ithub.ithub_space.exception.NotFoundException;
import ru.ithub.ithub_space.model.Role;
import ru.ithub.ithub_space.model.UserEntity;
import ru.ithub.ithub_space.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("Пользователь не найден: {}", email);
                    return new UsernameNotFoundException("Пользователь не найден: " + email);
                });
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }

    public UserEntity register(String firstName, String lastName,
                         String middleName, String email,
                         String password, String city,
                         String direction, Role role) {

        log.info("Регистрация нового пользователя: {}", email);

        if (userRepository.existsByEmail(email)) {
            log.warn("Попытка регистрации с уже существующим email: {}", email);
            throw new ConflictException("Пользователь с таким email уже существует");
        }

        UserEntity user = new UserEntity();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setMiddleName(middleName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setCity(city);
        user.setDirection(direction);
        user.setRole(role);

        return userRepository.save(user);
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }
}
