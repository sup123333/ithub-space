package ru.ithub.ithub_space.dto;

import ru.ithub.ithub_space.model.BuildingEntity;

public record BuildingResponse(
        Long id,
        String name,
        String address,
        String description,
        String mapUrl,
        String imageUrl
) {
    public static BuildingResponse from(BuildingEntity building) {
        return new BuildingResponse(
                building.getId(),
                building.getName(),
                building.getAddress(),
                building.getDescription(),
                building.getMapUrl(),
                building.getImageUrl()
        );
    }
}
