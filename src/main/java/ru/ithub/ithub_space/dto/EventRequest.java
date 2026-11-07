package ru.ithub.ithub_space.dto;

import ru.ithub.ithub_space.model.EventEntity;

import java.time.LocalDateTime;

public record EventRequest(
        String title,
        String description,
        LocalDateTime eventDate,
        String location,
        String imageUrl,
        String program
) {
    public EventEntity toEntity() {
        EventEntity event = new EventEntity();
        event.setTitle(title);
        event.setDescription(description);
        event.setEventDate(eventDate);
        event.setLocation(location);
        event.setImageUrl(imageUrl);
        event.setProgram(program);
        return event;
    }
}
