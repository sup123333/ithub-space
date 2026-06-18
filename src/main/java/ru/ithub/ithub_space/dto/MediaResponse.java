package ru.ithub.ithub_space.dto;

import ru.ithub.ithub_space.model.MediaEntity;

import java.time.LocalDateTime;

public record MediaResponse(
        Long id,
        String url,
        String title,
        String description,
        MediaEntity.MediaType type,
        String thumbnailUrl,
        LocalDateTime createdAt
) {
    public static MediaResponse from(MediaEntity media) {
        return new MediaResponse(
                media.getId(),
                media.getUrl(),
                media.getTitle(),
                media.getDescription(),
                media.getType(),
                media.getThumbnailUrl(),
                media.getCreatedAt()
        );
    }
}
