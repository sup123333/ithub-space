package ru.ithub.ithub_space.dto;

import ru.ithub.ithub_space.model.BuildingEntity;

public record BuildingRequest(
        String name,
        String address,
        String description,
        String mapUrl,
        String imageUrl
) {
    public BuildingEntity toEntity() {
        BuildingEntity building = new BuildingEntity();
        building.setName(name);
        building.setAddress(address);
        building.setDescription(description);
        building.setMapUrl(mapUrl);
        building.setImageUrl(imageUrl);
        return building;
    }
}
