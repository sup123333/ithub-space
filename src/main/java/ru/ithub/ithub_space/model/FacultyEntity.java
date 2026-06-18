package ru.ithub.ithub_space.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "faculties")
public class FacultyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String headName;

    private Integer studentCount;

    private String imageUrl;
}
