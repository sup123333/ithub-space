package ru.ithub.ithub_space.dto;

import ru.ithub.ithub_space.model.ExcursionRequestEntity;
import java.time.LocalDateTime;

public record ExcursionRequestDto(
        Long id,
        String name,
        String phone,
        String email,
        LocalDateTime createdAt
) {
    public ExcursionRequestEntity toEntity() {
        ExcursionRequestEntity excursion = new ExcursionRequestEntity();
        excursion.setName(name);
        excursion.setPhone(phone);
        excursion.setEmail(email);
        excursion.setCreatedAt(LocalDateTime.now());
        return excursion;
    }

    public static ExcursionRequestDto from(ExcursionRequestEntity excursion) {
        return new ExcursionRequestDto(
                excursion.getId(),
                excursion.getName(),
                excursion.getPhone(),
                excursion.getEmail(),
                excursion.getCreatedAt()
        );
    }
}
