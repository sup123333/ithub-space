package ru.ithub.ithub_space.dto;

import ru.ithub.ithub_space.model.FacultyEntity;

public record FacultyResponse(
        Long id,
        String name,
        String description,
        String headName,
        Integer studentCount,
        String imageUrl,
        String businessRoles,
        String skills,
        String curatorPhoto
) {
    public static FacultyResponse from(FacultyEntity faculty) {
        return new FacultyResponse(
                faculty.getId(),
                faculty.getName(),
                faculty.getDescription(),
                faculty.getHeadName(),
                faculty.getStudentCount(),
                faculty.getImageUrl(),
                faculty.getBusinessRoles(),
                faculty.getSkills(),
                faculty.getCuratorPhoto()
        );
    }
}
