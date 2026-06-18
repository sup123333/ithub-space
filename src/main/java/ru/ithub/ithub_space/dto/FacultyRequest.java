package ru.ithub.ithub_space.dto;

import ru.ithub.ithub_space.model.FacultyEntity;

public record FacultyRequest(
        String name,
        String description,
        String headName,
        Integer studentCount,
        String imageUrl,
        String businessRoles,
        String skills,
        String curatorPhoto
) {
    public FacultyEntity toEntity() {
        FacultyEntity faculty = new FacultyEntity();
        faculty.setName(name);
        faculty.setDescription(description);
        faculty.setHeadName(headName);
        faculty.setStudentCount(studentCount);
        faculty.setImageUrl(imageUrl);
        faculty.setBusinessRoles(businessRoles);
        faculty.setSkills(skills);
        faculty.setCuratorPhoto(curatorPhoto);
        return faculty;
    }
}
