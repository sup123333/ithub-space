package ru.ithub.ithub_space.dto;

import ru.ithub.ithub_space.model.EventEntity;

import java.time.LocalDateTime;

public record EventResponse(
        Long id,
        String title,
        String description,
        LocalDateTime eventDate,
        String location,
        String imageUrl,
        String program
) {
    public static EventResponse from(EventEntity event) {
        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getEventDate(),
                event.getLocation(),
                event.getImageUrl(),
                event.getProgram()
        );
    }
}
