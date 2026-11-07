package ru.ithub.ithub_space.dto;

import ru.ithub.ithub_space.model.MediaEntity;

import java.time.LocalDateTime;

public record MediaRequest(
        String url,
        String title,
        String description,
        MediaEntity.MediaType type,
        String thumbnailUrl
) {
    public MediaEntity toEntity() {
        MediaEntity media = new MediaEntity();
        media.setUrl(url);
        media.setTitle(title);
        media.setDescription(description);
        media.setType(type);
        media.setThumbnailUrl(thumbnailUrl);
        media.setCreatedAt(LocalDateTime.now());
        return media;
    }
}
