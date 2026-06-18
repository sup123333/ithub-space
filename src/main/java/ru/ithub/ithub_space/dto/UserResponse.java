package ru.ithub.ithub_space.dto;

import ru.ithub.ithub_space.model.Role;
import ru.ithub.ithub_space.model.UserEntity;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String middleName,
        String email,
        String city,
        String direction,
        String groupName,
        String subject,
        Role role
) {
    public static UserResponse from(UserEntity user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getMiddleName(),
                user.getEmail(),
                user.getCity(),
                user.getDirection(),
                user.getGroupName(),
                user.getSubject(),
                user.getRole()
        );
    }
}
